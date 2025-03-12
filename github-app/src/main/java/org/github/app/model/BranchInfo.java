package org.github.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BranchInfo {

    @JsonProperty("name")
    public String branchName;

    @JsonProperty("sha")
    public String lastCommitSha;

    public BranchInfo() {
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getLastCommitSha() {
        return lastCommitSha;
    }

    public void setLastCommitSha(String lastCommitSha) {
        this.lastCommitSha = lastCommitSha;
    }

    public BranchInfo(String branchName, String lastCommitSha) {
        this.branchName = branchName;
        this.lastCommitSha = lastCommitSha;
    }

}