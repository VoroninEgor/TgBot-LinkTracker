package edu.java.client.github;

import edu.java.dto.RepoResponse;

public interface GitHubClient {
    RepoResponse fetchRepo(String username, String repoName);
}
