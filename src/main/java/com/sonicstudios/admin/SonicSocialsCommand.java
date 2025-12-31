package com.sonicstudios.admin;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import com.sonicstudios.Main;
import com.sonicstudios.utility.MessageUtil;

import java.util.Objects;

public class SonicSocialsCommand implements CommandExecutor {

    public SonicSocialsCommand() {
        Objects.requireNonNull(Main.getInstance().getCommand("sonicsocials")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!sender.hasPermission("sonicsocials.reload")) {
            if(MessageUtil.getChat()) {
                sender.sendMessage(MessageUtil.getPrefix() + MessageUtil.get("sonicsocials.no-permission"));
            }

            return true;
        }

        if(args.length != 1) {
            if(MessageUtil.getChat()) {
                sender.sendMessage(MessageUtil.getPrefix()+ MessageUtil.get("sonicsocials.wrong-usage"));
            }

            return true;
        }

        Main.getInstance().reloadConfig();
        MessageUtil.reloadCooldown();

        Main.getInstance().getFileManager().reloadFile("media.yml");
        Main.getInstance().getFileManager().reloadFile("socials.yml");



        if(sender instanceof Player player) {
            if(MessageUtil.getActionBar()) {
                player.sendActionBar(MessageUtil.getPrefix()+ MessageUtil.get("sonicsocials.success"));
                player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            }
        }

        if(MessageUtil.getChat()) {
            sender.sendMessage(MessageUtil.getPrefix()+ MessageUtil.get("sonicsocials.success"));
        }




        return true;
    }
}