package it.skybridge.nxtor2.utils;

import org.bukkit.Location;

import de.inventivegames.hologram.Hologram;
import net.md_5.bungee.api.ChatColor;

public class HologramUtils {

	public static Hologram getHologram(Location location, String text) {
		Hologram hologram = de.inventivegames.hologram.HologramAPI.createHologram(location, ChatColor.translateAlternateColorCodes('&', text));
        return hologram;		
	}
}
