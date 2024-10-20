package edu.java.bot.utill;

import lombok.Getter;

@Getter
public enum TrackingLinks {
    GITHUB("^https://github\\.com/[^/]+/[^/]+.*$", "Git Hub"),
    STACKOVERFLOW("^https://stackoverflow\\.com/questions/\\d+/.*$", "Stack Overflow");

    private final String regexPattern;
    private final String name;

    TrackingLinks(String regexPattern, String name) {
        this.regexPattern = regexPattern;
        this.name = name;
    }
}
