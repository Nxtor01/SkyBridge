package it.skybridge.nxtor2;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import it.skybridge.nxtor2.manager.CommandManager;
import it.skybridge.nxtor2.manager.FilesManager;
import it.skybridge.nxtor2.manager.ListenerManager;
import it.skybridge.nxtor2.manager.StatsManager;
import it.skybridge.nxtor2.objects.Arena;
import it.skybridge.nxtor2.objects.User;
import it.skybridge.nxtor2.tasks.VoidTask;
import net.md_5.bungee.api.ChatColor;

public class SkyBridge extends JavaPlugin {

	public File arenas_file;
    public FileConfiguration arenas;
	public File stats_file;
    public FileConfiguration stats;
	private static SkyBridge plugin;
	HashMap<User, Arena> respawn;
	HashMap<User, Color> respawn2;
	HashMap<Arena, List<Location>> placedBlocks;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
        plugin = this;
		log(Level.INFO, "&7Abilitando &bSkyBridge&7...");
        CommandManager cm = new CommandManager(this);
        ListenerManager lm = new ListenerManager(this);
        FilesManager fm = new FilesManager(this);
        
        cm.setupCommands();
        lm.setupListeners();
        fm.setupFiles();
        
        respawn = new HashMap<>();
        respawn2 = new HashMap<>();
        placedBlocks = new HashMap<>();
        
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new VoidTask(), 5, 5);
		log(Level.INFO, "&bSkyBridge &7abilitato con successo.");
	}

	@Override
	public void onDisable() {
		log(Level.INFO, "&7Disabilitando &bSkyBridge&7...");
		if(Arena.getPlayingGames().size() > 0) {
		log(Level.WARNING, "Ci sono partite in corso, disabilitandole...");
		for(Arena a : Arena.getPlayingGames()) {
			a.finishGame(null);
		}
		}
		log(Level.INFO, "&bSkyBridge &7abilitato con successo.");
	}
	
	public static SkyBridge getInstance() {
		return plugin;
	}
	
	public void log(Level info, String message) {
		Bukkit.getLogger().log(info, ChatColor.translateAlternateColorCodes('&', message));
	}
	
	public String getPrefix() {
		return ChatColor.translateAlternateColorCodes('&', getConfig().getString("Prefix"));
	}
	
	public FilesManager getFilesManager() {
		return new FilesManager(this);
	}
	
	public StatsManager getStatsManager() {
		return new StatsManager(this);
	}
	
	public HashMap<User, Arena> getRespawns() {
		return respawn;
	}
	
	public HashMap<User, Color> getRespawns2() {
		return respawn2;
	}
	
	public HashMap<Arena, List<Location>> getPlacedBlocks() {
		return placedBlocks;
	}
}