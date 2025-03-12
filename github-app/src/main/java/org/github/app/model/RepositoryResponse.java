package org.github.app.model;

import java.util.List;

public class RepositoryResponse {

    private String repositoryName;

    private String login;

    private List<BranchInfo> branches;

    public RepositoryResponse() {
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public List<BranchInfo> getBranches() {
        return branches;
    }

    public void setBranches(List<BranchInfo> branches) {
        this.branches = branches;
    }

    public String getLogin() {
        return login;
    }

    public RepositoryResponse(String repositoryName, String login, List<BranchInfo> branches) {
        this.repositoryName = repositoryName;
        this.login = login;
        this.branches = branches;
    }
}
