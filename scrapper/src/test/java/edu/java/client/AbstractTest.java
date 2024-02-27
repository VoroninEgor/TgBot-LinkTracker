package edu.java.client;

import lombok.SneakyThrows;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class AbstractTest {
    @SneakyThrows
    public String jsonToString(String filePath) {
        return Files.readString(Paths.get(filePath));
    }
}
