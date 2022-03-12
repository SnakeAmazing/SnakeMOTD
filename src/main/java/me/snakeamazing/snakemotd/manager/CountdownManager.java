package me.snakeamazing.snakemotd.manager;

import net.kyori.adventure.text.Component;

import java.util.Date;

public interface CountdownManager {

    void init();

    void stop();

    int getTimeInSeconds();

    String getTime();
}
