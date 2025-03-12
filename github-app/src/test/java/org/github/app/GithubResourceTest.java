package org.github.app;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.github.app.model.*;
import org.github.app.rest.GithubClient;
import org.github.app.rest.GithubResource;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@QuarkusTest
public class GithubResourceTest {

    @InjectMock
    @RestClient
    GithubClient githubClient;

    @Inject
    GithubResource githubResource;

    @BeforeEach
    void setUp() {
        Repository repo1 = new Repository();
        repo1.setName("repo1");
        repo1.setFork(false);
        repo1.setOwner(new Owner("testuser"));

        Repository repo2 = new Repository();
        repo2.setName("repo2");
        repo2.setFork(false);
        repo2.setOwner(new Owner("testuser"));

        List<Repository> reposList = List.of(repo1, repo2);

        when(githubClient.getRepositories("testuser")).thenReturn(Uni.createFrom().item(reposList));

        Branch branch1 = new Branch();
        branch1.setName("main");
        branch1.setCommit(new Branch.Commit("abc123"));

        Branch branch2 = new Branch();
        branch2.setName("dev");
        branch2.setCommit(new Branch.Commit("def456"));

        List<Branch> branchesList = List.of(branch1, branch2);

        // Mock the response from GithubClient for getBranches
        when(githubClient.getBranches("testuser", "repo1")).thenReturn(Uni.createFrom().item(branchesList));
        when(githubClient.getBranches("testuser", "repo2")).thenReturn(Uni.createFrom().item(branchesList));
    }

    @SuppressWarnings("unchecked")
    @Test
    void testGetRepositoriesSuccess() {

        // Given & When
        Uni<Response> responseUni = githubResource.getRepositories("testuser");
        Response response = responseUni.await().indefinitely();

        // Then

        assertEquals(200, response.getStatus(), "Response should be 200 OK");

        List<RepositoryResponse> responseBody = (List<RepositoryResponse>) response.getEntity();
        assertNotNull(responseBody, "Response body should not be null");
        assertEquals(2, responseBody.size(), "Response body should contain 2 repositories");

        RepositoryResponse repo1Response = responseBody.get(0);
        assertEquals("repo1", repo1Response.getRepositoryName(), "First repository name should be 'repo1'");
        assertEquals("testuser", repo1Response.getLogin(), "First repository owner should be 'testuser'");
        assertEquals(2, repo1Response.getBranches().size(), "repo1 should have 2 branches");

        assertEquals("main", repo1Response.getBranches().get(0).getBranchName(), "First branch in repo1 should be 'main'");
        assertEquals("abc123", repo1Response.getBranches().get(0).getLastCommitSha(), "First branch commit in repo1 should be 'abc123'");
        assertEquals("dev", repo1Response.getBranches().get(1).getBranchName(), "Second branch in repo1 should be 'dev'");
        assertEquals("def456", repo1Response.getBranches().get(1).getLastCommitSha(), "Second branch commit in repo1 should be 'def456'");

        RepositoryResponse repo2Response = responseBody.get(1);
        assertEquals("repo2", repo2Response.getRepositoryName(), "Second repository name should be 'repo2'");
        assertEquals("testuser", repo2Response.getLogin(), "Second repository owner should be 'testuser'");
        assertEquals(2, repo2Response.getBranches().size(), "repo2 should have 2 branches");

        assertEquals("main", repo2Response.getBranches().get(0).getBranchName(), "First branch in repo2 should be 'main'");
        assertEquals("abc123", repo2Response.getBranches().get(0).getLastCommitSha(), "First branch commit in repo2 should be 'abc123'");
        assertEquals("dev", repo2Response.getBranches().get(1).getBranchName(), "Second branch in repo2 should be 'dev'");
        assertEquals("def456", repo2Response.getBranches().get(1).getLastCommitSha(), "Second branch commit in repo2 should be 'def456'");
    }
}
