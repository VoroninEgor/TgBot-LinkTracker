package edu.java.bot.model;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

@Getter
public class TrackingLinks {
    private final Set<String> trackLinks = new HashSet<>();

    public void track(String url) {
        trackLinks.add(url);
    }

    public void untrack(String url) {
        trackLinks.remove(url);
    }
}
