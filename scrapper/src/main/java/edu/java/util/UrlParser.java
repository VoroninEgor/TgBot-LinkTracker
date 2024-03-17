package edu.java.util;

import edu.java.exception.UrlParseException;
import org.springframework.stereotype.Component;

@Component
public class UrlParser {
    private static final String GITHUBLINK = "^https://github\\.com/[^/]+/[^/]+.*$";
    private static final String STACKOVERFLOWLINK = "^https://stackoverflow\\.com/questions/\\d+/.*$";

    public String fetchRepoNameFromGitHubLink(String url) {
        if (!url.matches(GITHUBLINK)) {
            throw new UrlParseException();
        }
        int startIndex = url.lastIndexOf("/") + 1;
        return url.substring(startIndex);
    }

    @SuppressWarnings("checkstyle:MagicNumber") public String fetchUserNameFromGitHubLink(String url) {
        if (!url.matches(GITHUBLINK)) {
            throw new UrlParseException();
        }
        int distanceToStartName = 5;
        int startIndex = url.indexOf(".com/") + distanceToStartName;
        int endIndex = url.lastIndexOf("/");
        return url.substring(startIndex, endIndex);
    }

    @SuppressWarnings("checkstyle:MagicNumber") public Long fetchQuestionIdFromStackOverFlowLink(String url) {
        if (!url.matches(STACKOVERFLOWLINK)) {
            throw new UrlParseException();
        }
        int distanceToId = 10;
        int startIndex = url.indexOf("questions/") + distanceToId;
        int endIndex = url.lastIndexOf("/");
        String id = url.substring(startIndex, endIndex);
        return Long.parseLong(id);
    }
}
