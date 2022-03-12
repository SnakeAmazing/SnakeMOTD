package me.snakeamazing.snakemotd.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigurationResourceResolver {

    public static void resolver(Path directory, String path) throws IOException {
        System.out.println("Path " + path);
        InputStream inputStream = ConfigurationResourceResolver.class
                .getClassLoader()
                .getResourceAsStream(path);

        if (inputStream != null) {
            File file = directory.toFile();
            System.out.println(file);

            if (file.exists()) {
                return;
            }

            Files.copy(inputStream, directory);
        }

    }
}
