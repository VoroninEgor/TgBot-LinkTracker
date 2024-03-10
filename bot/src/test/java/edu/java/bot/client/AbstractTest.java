package edu.java.bot.client;

import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.SneakyThrows;

public abstract class AbstractTest {
    @SneakyThrows
    public String jsonToString(String filePath) {
        return Files.readString(Paths.get(filePath));
    }
}
