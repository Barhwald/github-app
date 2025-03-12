package org.github.app.model;

public class Branch {
    public String name;
    public Commit commit;

    public Branch() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Commit getCommit() {
        return commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }

    public static class Commit {

        public Commit(String sha) {
            this.sha = sha;
        }

        public String sha;

    }

}