package org.github.app.rest;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.github.app.model.*;

import java.util.ArrayList;
import java.util.List;

@Path("/github")
public class GithubResource {

    @RestClient
    GithubClient githubClient;

    @GET
    @Path("/{username}/repositories")
    public Uni<Response> getRepositories(@PathParam("username") String username) {

        return githubClient.getRepositories(username)
                .onItem().transformToUni(repositories -> {

                    List<Uni<RepositoryResponse>> responses = new ArrayList<>();

                    for (Repository repo : repositories) {
                        if (repo.isFork()) {
                            continue;
                        }

                        Uni<List<Branch>> branchesUni = githubClient.getBranches(username, repo.getName());

                        responses.add(branchesUni.onItem().transform(branches -> {
                            List<BranchInfo> branchInfos = branches.stream()
                                    .map(branch -> new BranchInfo(branch.name, branch.commit.sha))
                                    .toList();
                            return new RepositoryResponse(repo.getName(), repo.getOwner().getLogin(), branchInfos);
                        }));
                    }

                    return Uni.join().all(responses)
                            .andCollectFailures()
                            .onItem().transform(list -> Response.ok(list).build());
                })
                .onFailure().recoverWithItem(e -> {

                    if (e instanceof WebApplicationException) {
                        WebApplicationException webEx = (WebApplicationException) e;
                        int statusCode = webEx.getResponse().getStatus();
                        ErrorResponse errorResponse = new ErrorResponse(Response.Status.fromStatusCode(statusCode), e.getMessage());
                        return Response.status(statusCode)
                                .entity(errorResponse)
                                .build();
                    }

                    ErrorResponse errorResponse = new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity(errorResponse)
                            .build();
                });
    }
}
