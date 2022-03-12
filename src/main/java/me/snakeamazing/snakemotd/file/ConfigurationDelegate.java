package me.snakeamazing.snakemotd.file;

import org.spongepowered.configurate.serialize.SerializationException;

import java.util.List;

public interface ConfigurationDelegate {

    ConfigurationDelegate getNode(Object... objects);

    String getString(String path);

    String getUnColoredString(String path);

    int getInt(String path);

    double getDouble(String path);

    boolean getBoolean(String path);

    void set(String path, String value);

    <V> List<V> getList(String path, Class<V> clazz) throws SerializationException;

    default List<String> getStringList(String path) throws SerializationException {
        return getList(path, String.class);
    }

    default List<String> getColoredList(String path) throws SerializationException {
        return getStringList(path);
    }

    void reload();

}
