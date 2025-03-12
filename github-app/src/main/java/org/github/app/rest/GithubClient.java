package org.github.app.rest;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.github.app.model.Branch;
import org.github.app.model.Repository;

import java.util.List;

@RegisterRestClient
public interface GithubClient {

    @GET
    @Path("/users/{username}/repos")
    Uni<List<Repository>> getRepositories(@PathParam("username") String username);

    @GET
    @Path("/repos/{owner}/{repo}/branches")
    Uni<List<Branch>> getBranches(@PathParam("owner") String owner, @PathParam("repo") String repo);
}