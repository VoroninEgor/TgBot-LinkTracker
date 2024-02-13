package edu.java.bot.utill;

import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class URLChecker {
    public static boolean isValid(String url) {
        HttpURLConnection connection = null;
        try {
            URL u = new URL(url);
            connection = (HttpURLConnection) u.openConnection();
            connection.setRequestMethod("HEAD");
            int code = connection.getResponseCode();
            System.out.println("" + code);
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
