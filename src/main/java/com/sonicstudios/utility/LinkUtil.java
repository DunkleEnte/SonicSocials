package com.sonicstudios.utility;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

@UtilityClass
public class LinkUtil {

    public static void sendLink(Player player, String url, String displayText) {

        // Colorize the displayed link
        final String color = MessageUtil.translate(displayText);

        // Uhm, creates the colorized link with a click event -> opens a url.
        TextComponent message = new TextComponent(TextComponent.fromLegacyText(color));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));

        // Sends the mesages.
        player.sendMessage(message);
    }
}