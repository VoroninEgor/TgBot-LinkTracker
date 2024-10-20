package edu.java.bot.utill;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@UtilityClass
public class URLChecker {

    public static boolean isValid(String url) {

        for (TrackingLinks resource : TrackingLinks.values()) {
            if (!url.matches(resource.getRegexPattern())) {
                return false;
            }
        }

        HttpURLConnection connection = null;
        try {
            URL u = new URL(url);
            connection = (HttpURLConnection) u.openConnection();
            connection.setRequestMethod("HEAD");
            int code = connection.getResponseCode();
            return code == HttpStatus.OK.value();
        } catch (IOException e) {
            log.warn("incorrect link", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return false;
    }
}
