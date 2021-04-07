package it.skybridge.nxtor2.manager;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import it.skybridge.nxtor2.SkyBridge;
import net.md_5.bungee.api.ChatColor;

public class FilesManager {

	SkyBridge instance;
	
	public FilesManager(SkyBridge instance) {
		this.instance = instance;
	}
	
	public void setupFiles() {
		instance.getDataFolder().mkdirs();
		
		instance.getConfig().options().copyDefaults(true);
		instance.saveDefaultConfig();

		instance.arenas_file = new File(instance.getDataFolder(), "arenas.yml");
		if(!instance.arenas_file.exists()) {
			instance.arenas_file.getParentFile().mkdirs();
			instance.saveResource("arenas.yml",false);
		}
		instance.arenas = new YamlConfiguration();
		try {
			instance.arenas.load(instance.arenas_file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}

		instance.stats_file = new File(instance.getDataFolder(), "stats.yml");
		if(!instance.stats_file.exists()) {
			instance.stats_file.getParentFile().mkdirs();
			instance.saveResource("stats.yml",false);
		}
		instance.stats = new YamlConfiguration();
		try {
			instance.stats.load(instance.stats_file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public void saveFile(FileConfiguration fc, File f) {
		try {
			fc.save(f);
		} catch(Exception e) { e.printStackTrace(); return; }
	}
	
	public void reloadFiles(CommandSender sender) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', instance.getPrefix() + "&7Ricaricando i files..."));
        instance.arenas = YamlConfiguration.loadConfiguration(instance.arenas_file);
        instance.stats = YamlConfiguration.loadConfiguration(instance.stats_file);
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', instance.getPrefix() + "&7Files ricaricati con successo."));
	}
	
	public FileConfiguration getArenasFile() {
		return instance.arenas;
	}
	
	public FileConfiguration getStatsFile() {
		return instance.stats;
	}
}
