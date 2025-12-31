package com.sonicstudios.command;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import com.sonicstudios.Main;
import com.sonicstudios.utility.MessageUtil;
import com.sonicstudios.utility.LinkUtil;

import java.util.*;

public class LiveCommand implements CommandExecutor, TabCompleter {

    private final Map<UUID, Long> cooldowns = new HashMap<>();

    public LiveCommand() {
        Objects.requireNonNull(Main.getInstance().getCommand("live")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;


        if (!player.hasPermission("live.use")) {
            if(MessageUtil.getChat()) {
                player.sendMessage(MessageUtil.getPrefix() + MessageUtil.get("live.no-permission"));
            }

            if(MessageUtil.getActionBar()) {
                player.sendActionBar(MessageUtil.getPrefix()+ MessageUtil.get("live.no-permission"));
            }

            player.playSound(player, Sound.ENTITY_VILLAGER_HURT, 1, 1);
            return true;
        }

        if (!player.hasPermission("live.bypass")) {
            final long now = System.currentTimeMillis();
            final long last = cooldowns.getOrDefault(player.getUniqueId(), 0L);

            if (now - last < MessageUtil.getCooldownMillis()) {
                long remaining = (MessageUtil.getCooldownMillis() - (now - last)) / 1000;
                if(MessageUtil.getChat()) {
                    player.sendMessage(MessageUtil.getPrefix()+ MessageUtil.get("live.on-cooldown")
                            .replace("%remaining%", String.valueOf(remaining)));
                }

                if (MessageUtil.getActionBar()) {
                    player.sendActionBar(MessageUtil.getPrefix()+ MessageUtil.get("live.on-cooldown")
                            .replace("%remaining%", String.valueOf(remaining)));
                }

                player.playSound(player, Sound.ENTITY_VILLAGER_HURT, 1, 1);
                return true;
            }
            cooldowns.put(player.getUniqueId(), now);
        }

        if (args.length == 0) {
            Bukkit.broadcastMessage(MessageUtil.get("live.fallback-live")
                    .replace("%player%", player.getName()));
            return true;
        }

        final String platform = args[0].toLowerCase(Locale.ROOT);
        final String url = args[1];

        switch (platform) {
            case "youtube" -> {
                Bukkit.broadcastMessage(MessageUtil.get("live.youtube-live")
                        .replace("%player%", player.getName())
                );

                Bukkit.getOnlinePlayers().forEach(p -> LinkUtil.sendLink(p, url, MessageUtil.get("live.youtube-link")));
            }
            case "twitch" -> {
                Bukkit.broadcastMessage(MessageUtil.get("live.twitch-live")
                        .replace("%player%", player.getName())
                );

                Bukkit.getOnlinePlayers().forEach(p -> LinkUtil.sendLink(p, url, MessageUtil.get("live.twitch-link")));
            }
            case "tiktok" -> {
                Bukkit.broadcastMessage(MessageUtil.get("live.tiktok-live")
                        .replace("%player%", player.getName())
                );

                Bukkit.getOnlinePlayers().forEach(p -> LinkUtil.sendLink(p, url, MessageUtil.get("live.tiktok-link")));
            }
            default -> {
                if(MessageUtil.getChat()) {
                    player.sendMessage(MessageUtil.getPrefix()+ MessageUtil.get("live.invalid-platform"));
                }

                if(MessageUtil.getActionBar()) {
                    player.sendActionBar(MessageUtil.getPrefix()+ MessageUtil.get("live.invalid-platform"));
                }

                player.playSound(player, Sound.ENTITY_VILLAGER_HURT, 1, 1);
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) return Arrays.asList("youtube", "twitch", "tiktok");
        if (args.length == 2) {
            return switch (args[0].toLowerCase(Locale.ROOT)) {
                case "youtube" -> List.of("https://youtube.com/");
                case "twitch" -> List.of("https://twitch.tv/");
                case "tiktok" -> List.of("https://tiktok.com/@");
                default -> Collections.emptyList();
            };
        }
        return Collections.emptyList();
    }
}