package me.snakeamazing.snakemotd.listeners;

import com.velocitypowered.api.event.EventHandler;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.proxy.server.ServerPing;
import me.snakeamazing.snakemotd.file.ConfigurationDelegate;
import me.snakeamazing.snakemotd.manager.CountdownManager;
import me.snakeamazing.snakemotd.util.ColorUtil;

public class ProxyPingListener implements EventHandler<ProxyPingEvent> {

    private final CountdownManager countdownManager;
    private final ConfigurationDelegate config;

    public ProxyPingListener(CountdownManager countdownManager, ConfigurationDelegate config) {
        this.countdownManager = countdownManager;
        this.config = config.getNode("config");
    }

    @Override
    public void execute(ProxyPingEvent event) {
        ServerPing serverPing = event.getPing();
        ServerPing.Builder builder = serverPing.asBuilder();

        String motd = config.getUnColoredString("motd")
                .replace("%newline%", "\n")
                .replace("%time%", countdownManager.getTime());

        System.out.println(motd);

        builder.maximumPlayers(config.getInt("slots"));
        builder.description(ColorUtil.parse(motd));
        builder.clearSamplePlayers();

        event.setPing(builder.build());
    }
}
