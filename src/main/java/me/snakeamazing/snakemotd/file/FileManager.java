package me.snakeamazing.snakemotd.file;

import me.snakeamazing.snakemotd.SnakeMOTD;

import java.io.File;
import java.io.IOException;

public class FileManager {

    private static final String DEFAULT_EXTENSION = ".yml";
    private final SnakeMOTD snakeMOTD;

    private final ConfigurationDelegate config;

    public  FileManager(SnakeMOTD snakeMOTD) {
        this.snakeMOTD = snakeMOTD;
        config = build("config");
    }

    private ConfigurationDelegate build(String name) {
        String nameWithExtension = name + DEFAULT_EXTENSION;
        System.out.println(name);

        File file = new File(snakeMOTD.getDataDirectory().toFile(), nameWithExtension);

        try {
            ConfigurationResourceResolver
                    .resolver(file.toPath(), nameWithExtension);

            return new YamlConfigurationDelegate(snakeMOTD, YamlConfigurationProvider.of(file));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    public ConfigurationDelegate getConfig() {
        return config;
    }
}
