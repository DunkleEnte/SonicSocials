package com.sonicstudios.utility;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.NotNull;
import com.sonicstudios.Main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
@Getter
public class MessageUtil {

    @Getter private static long cooldownMillis = parseTime(Main.getInstance().getConfig().getString("cooldown", ""));

    // Used for the Messages of the Plugin
    public static String get(@NotNull String key) {
        return translate(Main.getInstance().getConfig().getString(key, ""));
    }

    // Used for the Prefix of the Plugin
    public static String getPrefix() {
        return translate(Main.getInstance().getConfig().getString("sonicsocials.prefix", ""));
    }


    // Used for Chat Messsages -> If they are disabled or enabled
    public static Boolean getChat() {
        return (Main.getInstance().getConfig().getBoolean("allow-chat-messages", true));
    }

    // Used for ActionBar Messages -> If they are disabled or enabled
    public static Boolean getActionBar() {
        return (Main.getInstance().getConfig().getBoolean("allow-actionbar-messages", true));
    }

    // Used for the cooldown of the /live Command
    private static long parseTime(String input) {
        if (input == null || input.isBlank()) return 600_000L;
        input = input.trim().toLowerCase().replace(" ", "");

        long total = 0;
        StringBuilder num = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (Character.isDigit(c)) {
                num.append(c);
            } else if ("dhms".contains(String.valueOf(c))) {
                if (num.length() == 0) continue;
                final long value = Long.parseLong(num.toString());

                total += switch (c) {
                    case 'd' -> value * 86_400_000L;
                    case 'h' -> value * 3_600_000L;
                    case 'm' -> value * 60_000L;
                    case 's' -> value * 1_000L;
                    default -> 0L;
                };
                num = new StringBuilder();
            }
        }

        if (num.length() > 0) {
            total += Long.parseLong(num.toString()) * 60_000L;
        }
        return total;
    }

    // Little ColorUtil -> Translates ColorCodes.
    public static String translate(@NotNull String message) {
        Pattern pattern = Pattern.compile("&#([A-Fa-f0-9]{6})");
        Matcher matcher = pattern.matcher(message);
        StringBuilder buffer = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, ChatColor.of("#" + matcher.group(1)).toString());
        }
        matcher.appendTail(buffer);
        return ChatColor.translateAlternateColorCodes('&', buffer.toString());
    }

    // reloads the cooldown from the config -> used in the rleoad commmand
    public static void reloadCooldown() {
        cooldownMillis = parseTime(Main.getInstance().getConfig().getString("cooldown", ""));
    }

}