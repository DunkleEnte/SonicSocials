package com.sonicstudios.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import com.sonicstudios.Main;
import com.sonicstudios.utility.MessageUtil;

import java.util.Objects;

public class DiscordCommand implements CommandExecutor {

    public DiscordCommand() {
        Objects.requireNonNull(Main.getInstance().getCommand("discord")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player player)) return false;

        if(MessageUtil.getChat()) {
            player.sendMessage(MessageUtil.getPrefix() + MessageUtil.get("sonicsocials.discord-link"));
        }

        return true;
    }
}
