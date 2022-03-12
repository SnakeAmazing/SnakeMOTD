package me.snakeamazing.snakemotd;

import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import me.snakeamazing.snakemotd.commands.InitCommand;
import me.snakeamazing.snakemotd.commands.ReloadCommand;
import me.snakeamazing.snakemotd.file.ConfigurationDelegate;
import me.snakeamazing.snakemotd.file.FileManager;
import me.snakeamazing.snakemotd.listeners.ProxyPingListener;
import me.snakeamazing.snakemotd.manager.CountdownManager;
import me.snakeamazing.snakemotd.manager.SimpleCountdownManager;

import javax.inject.Inject;
import java.io.File;
import java.nio.file.Path;
import java.util.logging.Logger;

@Plugin(
        id = "snakemotd",
        name = "SnakeMOTD",
        version = "0.0.1",
        url = "https://github.com/SnakeAmazing",
        description = "MOTD plugin for Syndr4",
        authors = {"SnakeAmazing"}
)
public class SnakeMOTD {

    private final ProxyServer server;
    private final Logger logger;
    private final Path dataDirectory;

    private FileManager fileManager;
    private ConfigurationDelegate config;

    private CountdownManager countdownManager;

    @Inject
    public SnakeMOTD(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;

        this.dataDirectory = dataDirectory;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        File file = dataDirectory.toFile();

        if (!file.exists()) {
            System.out.println("Directory doesn't exist, creating it...");
            file.mkdirs();
        }

        this.fileManager = new FileManager(this);
        config = fileManager.getConfig();

        countdownManager = new SimpleCountdownManager(this, config);

        server.getEventManager().register(this, ProxyPingEvent.class, PostOrder.LAST, new ProxyPingListener(countdownManager, config));
        registerCommands();
    }

    public ProxyServer getServer() {
        return server;
    }

    public Logger getLogger() {
        return logger;
    }

    public Path getDataDirectory() {
        return dataDirectory;
    }

    public SnakeMOTD getPlugin() {
        return this;
    }

    public ConfigurationDelegate getConfig() {
        return config;
    }

    private void registerCommands() {
        CommandMeta meta = server.getCommandManager().metaBuilder("init")
                .aliases("initialize", "countdown")
                .build();

        server.getCommandManager().register(meta, new InitCommand(config, countdownManager));

        server.getCommandManager().register(
                server.getCommandManager().metaBuilder("motdreload").build(),
                new ReloadCommand(this)
        );
    }
}
