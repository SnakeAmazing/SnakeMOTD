package me.snakeamazing.snakemotd.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class ColorUtil {

    private final static MiniMessage miniMessage = MiniMessage.miniMessage();

    public static Component parse(String text) {
        return miniMessage.deserialize(text);
    }
}
