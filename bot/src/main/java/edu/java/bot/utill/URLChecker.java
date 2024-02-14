package edu.java.bot.utill;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class URLChecker {

    private URLChecker() {
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    public static boolean isValid(String url) {
        HttpURLConnection connection = null;
        try {
            URL u = new URL(url);
            connection = (HttpURLConnection) u.openConnection();
            connection.setRequestMethod("HEAD");
            int code = connection.getResponseCode();
            return code == 200;
        } catch (IOException e) {
            log.info("incorrect url");
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return false;
    }
}
