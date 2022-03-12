package me.snakeamazing.snakemotd.file;

import me.snakeamazing.snakemotd.SnakeMOTD;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.IOException;
import java.util.List;

public class YamlConfigurationDelegate implements ConfigurationDelegate {

    private static final String NODE_SEPARATOR = "\\.";

    private final SnakeMOTD snakeMOTD;

    private final CommentedConfigurationNode node;
    private ConfigurationLoader<CommentedConfigurationNode> loader;

    public YamlConfigurationDelegate(SnakeMOTD snakeMOTD, CommentedConfigurationNode node) {
        this.node = node;
        this.snakeMOTD = snakeMOTD;

        this.loader = YamlConfigurationLoader.builder().file(snakeMOTD.getDataDirectory().toFile()).build();

    }

    @Override
    public ConfigurationDelegate getNode(Object... objects) {
        return new YamlConfigurationDelegate(snakeMOTD, node.node(objects));
    }

    @Override
    public String getString(String path) {
        String result = node(path).getString();

        if (result == null) {
            return "The path is " + path + " null: please check the config";
        }

        return LegacyComponentSerializer
                .legacyAmpersand()
                .deserialize(result)
                .toString();
    }

    @Override
    public String getUnColoredString(String path) {
        String result = node(path).getString();

        if (result == null) {
            return "The path is " + path + " null: please check the config";
        }

        return result;
    }

    @Override
    public int getInt(String path) {
        return node(path).getInt();
    }

    @Override
    public double getDouble(String path) {
        return node(path).getDouble();
    }

    @Override
    public boolean getBoolean(String path) {
        return node(path).getBoolean();
    }

    @Override
    public void set(String path, String value) {
        try {
            node(path).set(value);
        } catch (SerializationException exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public <V> List<V> getList(String path, Class<V> clazz) throws SerializationException {
        return node(path).getList(clazz);
    }

    @Override
    public void reload() {
        try {
            loader.save(node);
            loader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    private CommentedConfigurationNode node(String path) {
        return node.node(separate(path));
    }

    private Object[] separate(String path) {
        return path.split(NODE_SEPARATOR);
    }
}
