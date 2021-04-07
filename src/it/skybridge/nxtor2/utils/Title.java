package it.skybridge.nxtor2.utils;

import org.bukkit.entity.Player;

import io.puharesource.mc.titlemanager.internal.InternalsKt;
import net.md_5.bungee.api.ChatColor;

public class Title {
	
	public static void sendTitle(Player player, String text, int fadeInTime, int showTime, int fadeOutTime, ChatColor color) {
        InternalsKt.getPluginInstance().sendTitle(player, color+text, fadeInTime, showTime, fadeOutTime);
    }
}
