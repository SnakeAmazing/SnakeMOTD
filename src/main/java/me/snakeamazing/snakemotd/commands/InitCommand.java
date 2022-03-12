package me.snakeamazing.snakemotd.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import me.snakeamazing.snakemotd.file.ConfigurationDelegate;
import me.snakeamazing.snakemotd.manager.CountdownManager;
import me.snakeamazing.snakemotd.util.ColorUtil;

public class InitCommand implements SimpleCommand {

    private final ConfigurationDelegate config;
    private final CountdownManager countdownManager;

    public InitCommand(ConfigurationDelegate config, CountdownManager countdownManager) {
        this.config = config.getNode("config");
        this.countdownManager = countdownManager;
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource commandSource = invocation.source();

        String[] args = invocation.arguments();

        if (args.length < 1) {
            commandSource.sendMessage(ColorUtil.parse("<red>¡Recuerda introducir la nueva fecha!"));
            return;
        }

        config.set("date", args[0] + args[1]);

        countdownManager.init();
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission("snakemotd.init");
    }
}
