package me.snakeamazing.snakemotd.file;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.nio.file.Path;

public class YamlConfigurationProvider {

    public static CommentedConfigurationNode of(File file) throws ConfigurateException {
        return of(file.toPath());
    }

    public static CommentedConfigurationNode of(Path path) throws ConfigurateException {
        return YamlConfigurationLoader
                .builder()
                .path(path)
                .build()
                .load();
    }
}
