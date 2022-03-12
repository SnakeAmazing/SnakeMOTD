package me.snakeamazing.snakemotd.commands;

import com.velocitypowered.api.command.SimpleCommand;
import me.snakeamazing.snakemotd.SnakeMOTD;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class ReloadCommand implements SimpleCommand {

    private final SnakeMOTD snakeMOTD;

    public ReloadCommand(SnakeMOTD snakeMOTD) {
        this.snakeMOTD = snakeMOTD;
    }

    @Override
    public void execute(Invocation invocation) {
        snakeMOTD.getConfig().reload();
        invocation.source().sendMessage(Component.text("Reloaded configuration!").color(TextColor.color(3)));
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission("snakemotd.reload");
    }
}
