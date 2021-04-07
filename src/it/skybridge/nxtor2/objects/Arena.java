package it.skybridge.nxtor2.objects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

import de.inventivegames.hologram.Hologram;
import de.inventivegames.hologram.HologramAPI;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import it.skybridge.nxtor2.SkyBridge;
import it.skybridge.nxtor2.utils.GameStatus;
import it.skybridge.nxtor2.utils.HologramUtils;
import it.skybridge.nxtor2.utils.Type;
import it.skybridge.nxtor2.utils.Utils;
import net.md_5.bungee.api.ChatColor;

public class Arena {

	String arena;
	Type type;
	static World world;
	Team red;
	Team blue;
	
	public Arena(String arena, Type type, World w, Team red, Team blue) { // Start arena
		this.arena = arena;
		this.type = type;
		world = w;
		this.red = blue;
		this.blue = blue;
	}
	
	public Arena(String arena, Type type, World w) { // Get arena
		this.arena = arena;
		this.type = type;
		world = w;
		try {
		this.red = getTeams().get(0);
		this.blue = getTeams().get(1);
		} catch(Exception ex) {}
	}
	
	public Arena(World w) { // Get an arena from world
		String line = SkyBridge.getInstance().getFilesManager().getArenasFile().getString("arenas."+w.getName()+".name");
		Type line2 = Type.getTypeFromString(SkyBridge.getInstance().getFilesManager().getArenasFile().getString("arenas."+w.getName()+".type"));
		this.arena = line;
		this.type = line2;
		world = w;
		try {
		this.red = getTeams().get(0);
		this.blue = getTeams().get(1);
		} catch(Exception ex) {}
	}
	
	public static Arena getArena(String name, Type type, World world) { return new Arena(name, type, world); }
	
	public static Arena getArenaFromWorld(World world) { return new Arena(world); }
	
	public static List<Arena> getGames() {
		List<Arena> arenas = new ArrayList<>();
		Set<String> keys = SkyBridge.getInstance().getFilesManager().getArenasFile().getConfigurationSection("arenas").getKeys(false);
		for(String key : keys) {
			Type type = Type.getTypeFromString(SkyBridge.getInstance().getFilesManager().getArenasFile().getString("arenas."+key+".type"));
			World world = Bukkit.getWorld(key);
			String name = SkyBridge.getInstance().getFilesManager().getArenasFile().getString("arenas."+key+".name");
			Arena a = getArena(name, type, world);
			arenas.add(a);
		}
		return arenas;
	}
	
	public static List<Arena> getPlayingGames() {
		List<Arena> arenas = getGames();
		for(int i = 0; i < arenas.size(); i++) {
			Arena a = arenas.get(i);
			if(!a.getStatus().equals(GameStatus.PLAYING)) {
				arenas.remove(a);
			}
		}
		return arenas;
	}
	
	public void joinGame(User u) {
		Utils.teleportToUnderMap(u, Color.RED, this);
		Utils.joinInventory(u);
		for(Player p : u.getWorld().getPlayers()) {
			User u2 = User.getUser(p.getName());
			u2.sendMessage("&b"+u.getName()+" &7e' entrato. (&b"+u.getWorld().getPlayers().size()+"&7/&b"+getMaxPlayers()+"&7)");
		}
		new BukkitRunnable() {
			@Override
			public void run() {
		if(getType().equals(Type.SOLO) && getWorld().getPlayers().size() >= 2) {
			List<User> redusers = new ArrayList<>();
			List<User> blueusers = new ArrayList<>();
			User u1 = User.getUser(getWorld().getPlayers().get(0).getName());
			User u2 = User.getUser(getWorld().getPlayers().get(1).getName());
			redusers.add(u1);
			blueusers.add(u2);
			Team red = Team.getTeam(redusers, Color.RED);
			Team blue = Team.getTeam(blueusers, Color.BLUE);
			setTeamRed(red);
			setTeamBlue(blue);
			startGame();
			return;
		} else if(getType().equals(Type.DUO) && getWorld().getPlayers().size() >= 4) {
			List<User> redusers = new ArrayList<>();
			List<User> blueusers = new ArrayList<>();
			User u1 = User.getUser(getWorld().getPlayers().get(0).getName());
			User u2 = User.getUser(getWorld().getPlayers().get(1).getName());
			User u3 = User.getUser(getWorld().getPlayers().get(2).getName());
			User u4 = User.getUser(getWorld().getPlayers().get(3).getName());
			redusers.add(u1);
			blueusers.add(u2);
			redusers.add(u3);
			blueusers.add(u4);
			Team red = Team.getTeam(redusers, Color.RED);
			Team blue = Team.getTeam(blueusers, Color.BLUE);
			setTeamRed(red);
			setTeamBlue(blue);
			startGame();
			return;
		} else if(getType().equals(Type.TRIPLO) && getWorld().getPlayers().size() >= 6) {
			List<User> redusers = new ArrayList<>();
			List<User> blueusers = new ArrayList<>();
			User u1 = User.getUser(getWorld().getPlayers().get(0).getName());
			User u2 = User.getUser(getWorld().getPlayers().get(1).getName());
			User u3 = User.getUser(getWorld().getPlayers().get(2).getName());
			User u4 = User.getUser(getWorld().getPlayers().get(3).getName());
			User u5 = User.getUser(getWorld().getPlayers().get(4).getName());
			User u6 = User.getUser(getWorld().getPlayers().get(5).getName());
			redusers.add(u1);
			blueusers.add(u2);
			redusers.add(u3);
			blueusers.add(u4);
			redusers.add(u5);
			blueusers.add(u6);
			Team red = Team.getTeam(redusers, Color.RED);
			Team blue = Team.getTeam(blueusers, Color.BLUE);
			setTeamRed(red);
			setTeamBlue(blue);
			startGame();
			return;
		} else if(getType().equals(Type.QUADRUPLO) && getWorld().getPlayers().size() >= 8) {
			List<User> redusers = new ArrayList<>();
			List<User> blueusers = new ArrayList<>();
			User u1 = User.getUser(getWorld().getPlayers().get(0).getName());
			User u2 = User.getUser(getWorld().getPlayers().get(1).getName());
			User u3 = User.getUser(getWorld().getPlayers().get(2).getName());
			User u4 = User.getUser(getWorld().getPlayers().get(3).getName());
			User u5 = User.getUser(getWorld().getPlayers().get(4).getName());
			User u6 = User.getUser(getWorld().getPlayers().get(5).getName());
			User u7 = User.getUser(getWorld().getPlayers().get(6).getName());
			User u8 = User.getUser(getWorld().getPlayers().get(7).getName());
			redusers.add(u1);
			blueusers.add(u2);
			redusers.add(u3);
			blueusers.add(u4);
			redusers.add(u5);
			blueusers.add(u6);
			redusers.add(u7);
			blueusers.add(u8);
			Team red = Team.getTeam(redusers, Color.RED);
			Team blue = Team.getTeam(blueusers, Color.BLUE);
			setTeamRed(red);
			setTeamBlue(blue);
			startGame();
			return;
		}
		}
	}.runTaskLater(SkyBridge.getInstance(), 60L);
	}
	
	private int getMaxPlayers() {
		switch(getType()) {
		case SOLO:
			return 2;
		case DUO:
			return 4;
		case TRIPLO:
			return 6;
		case QUADRUPLO:
			return 8;
		default:
			return 0;
		}
	}

	public void exitGame(User u) {
		Utils.spawnInventory(u);
		u.teleport(Utils.getLocationSpawn());
	}
	
	public void startGame() {
		world.setAutoSave(false);
		List<Location> blocks = new ArrayList<>();
		blocks.add(new Location(getWorld(), 0, 64, 0));
		SkyBridge.getInstance().getPlacedBlocks().put(this, blocks);
		Utils.runCage(this, null);
		FileConfiguration fc = SkyBridge.getInstance().getFilesManager().getArenasFile();
		fc.set("arenas."+getWorld().getName()+".status", "playing");
		try {
			fc.save(SkyBridge.getInstance().arenas_file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		double x1 = fc.getDouble("arenas."+getWorld().getName()+".hredX");
		double y1 = fc.getDouble("arenas."+getWorld().getName()+".hredY");
		double z1 = fc.getDouble("arenas."+getWorld().getName()+".hredZ");
		double x2 = fc.getDouble("arenas."+getWorld().getName()+".hblueX");
		double y2 = fc.getDouble("arenas."+getWorld().getName()+".hblueY");
		double z2 = fc.getDouble("arenas."+getWorld().getName()+".hblueZ");
		Location l = new Location(getWorld(), x1, y1, z1);
		Location l2 = new Location(getWorld(), x2, y2, z2);
		Hologram hred = HologramUtils.getHologram(l, "§7Squadra §cRossa§7, Goals:§b 0");
		Hologram hblue = HologramUtils.getHologram(l2, "§7Squadra §9Blu§7, Goals:§b 0");
		hred.spawn();
		hblue.spawn();
		for(User u : red.getTeam()) {
		BPlayerBoard board = Netherboard.instance().createBoard(u.getPlayer(), "§eTheBridge");
		board.setAll(
				" ",
				"§7Team §cRosso §8» §b0 §7Goal",
				"§7Team §9Blu §8» §b0 §7Goal",
				"  ",
				"§7Goal della tua squadra §8» §b0",
				"   ",
				"§7Mappa §8» §b" + arena,
				"§7Modalita' §8» §b"+getMode()
				);
		}
		for(User u : blue.getTeam()) {
		BPlayerBoard board = Netherboard.instance().createBoard(u.getPlayer(), "§eTheBridge");
		board.setAll(
				" ",
				"§7Team §cRosso §8» §b0 §7Goal",
				"§7Team §9Blu §8» §b0 §7Goal",
				"  ",
				"§7Goal della tua squadra §8» §b0",
				"   ",
				"§7Mappa §8» §b" + arena,
				"§7Modalita' §8» §b"+getMode()
				);
		}
		red.sendMessage("Buona fortuna e che vinca il migliore!");
		blue.sendMessage("Buona fortuna e che vinca il migliore!"); 
	}
	
	public void finishGame(Team winner) {
		if(getStatus().equals(GameStatus.PLAYING)) {
			// this.resetPlacedBlocks();
			Utils.createCages(this);
			Team w = null;
			Team l = null;
			Team team1 = getTeams().get(0);
			Team team2 = getTeams().get(1);
			for(Hologram h : HologramAPI.getHolograms()) {
				if(h.getLocation().getWorld().equals(getWorld())) {
					try {
					h.despawn();
					} catch(Exception ex) { SkyBridge.getInstance().log(Level.WARNING, "Un ologramma, nel mondo " + getWorld().getName() + ", ha dato errore");}
				}
			}
			
			for(User u : team1.getTeam()) {
				Netherboard.instance().removeBoard(u.getPlayer());
				u.clearInv();
			}
			for(User u : team2.getTeam()) {
				Netherboard.instance().removeBoard(u.getPlayer());
				u.clearInv();
			}
			if(winner == null) {
				team1.teleport(Utils.getLocationSpawn());
				team2.teleport(Utils.getLocationSpawn());
				return;
			}
			if(team1.equals(winner)) {
				w = team1;
				l = team2;
			} else if(team2.equals(winner)) {
				w = team2;
				l = team1;
			}
			for(User u : w.getTeam()) {
			SkyBridge.getInstance().getStatsManager().addWin(u);
			}
			for(User u : l.getTeam()) {
			SkyBridge.getInstance().getStatsManager().addLose(u);
			}
			SkyBridge.getInstance().getStatsManager().addGame();
			for(User u : team1.getTeam()) {
				SkyBridge.getInstance().getStatsManager().addPlayedGame(u);
				Utils.teleportToUnderMap(u, Color.RED, this);
			}
			for(User u : team2.getTeam()) {
				SkyBridge.getInstance().getStatsManager().addPlayedGame(u);
				Utils.teleportToUnderMap(u, Color.RED, this);
			}	
			
			Location loc = team1.getTeam().get(0).getLoc();
			FileConfiguration fc = SkyBridge.getInstance().getFilesManager().getArenasFile();
			fc.set("arenas."+getWorld().getName()+".status", "finishing");
			try {
				fc.save(SkyBridge.getInstance().arenas_file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Utils.fireworks(loc);
			if(w.getColor().equals(Color.BLUE)) {
				team1.sendTitle("La squadra blu vince!", ChatColor.BLUE);
				team2.sendTitle("La squadra blu vince!", ChatColor.BLUE);
			} else {
				team1.sendTitle("La squadra rossa vince!", ChatColor.RED);
				team2.sendTitle("La squadra rossa vince!", ChatColor.RED);
			}
			new BukkitRunnable() {
				@Override
				public void run() {
			team1.teleport(Utils.getLocationSpawn());
			team2.teleport(Utils.getLocationSpawn());
			fc.set("arenas."+getWorld().getName()+".status", "waiting");
			try {
				fc.save(SkyBridge.getInstance().arenas_file);
			} catch (IOException e) {
				e.printStackTrace();
			}
				}
			}.runTaskLaterAsynchronously(SkyBridge.getInstance(), 100L);
		} else {
			SkyBridge.getInstance().log(Level.WARNING, "L'arena " + arena + " nel mondo " + world.getName() + " ha cercato di terminarsi ma non è giocata");
			return;
		}
	}
	
	public String getMode() { 
		switch(getType()) {
		case SOLO:
			return "Solo";
		case DUO:
			return "Duo";
		case TRIPLO:
			return "Trio";
		case QUADRUPLO:
			return "4v4";
		default:
			return "Non definita";
		}
	}
	
	public String getName() { return arena; }
	
	public World getWorld() { return world; }
	
	public GameStatus getStatus() { return GameStatus.getStatusFromString(SkyBridge.getInstance().getFilesManager().getArenasFile().getString("arenas."+world.getName()+".status")); }
	
	public void setTeamRed(Team team) { this.red = team; }
	
	public void setTeamBlue(Team team) { this.blue = team; }
	
	public Team getTeamRed() { return red; }
	
	public Team getTeamBlue() { return blue; }
	
	public List<Team> getTeams() {
		List<User> redplayers = new ArrayList<>();
		List<User> blueplayers = new ArrayList<>();
		for(Player p : world.getPlayers()) {
			LeatherArmorMeta pi = (LeatherArmorMeta) p.getInventory().getBoots().getItemMeta();
			Color c = pi.getColor();
			if(c.equals(Color.RED)) {
				redplayers.add(User.getUser(p.getName()));
			} else if(c.equals(Color.BLUE)) {
				blueplayers.add(User.getUser(p.getName()));
			}
		}
		List<Team> result = new ArrayList<>();
		Team redteam = Team.getTeam(redplayers, Color.RED);
		Team blueteam = Team.getTeam(blueplayers, Color.BLUE);
		result.add(redteam);
		result.add(blueteam);
		return result;
	}
	
	public double getYVoid() {
		return SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+world.getName()+".yvoid");
	}
	
	public List<Location> getPlacedBlocks() {
		return SkyBridge.getInstance().getPlacedBlocks().get(this);
	}
	
	public void addPlacedBlock(Block b) {
		List<Location> blocks = getPlacedBlocks();
		blocks.add(b.getLocation());
		SkyBridge.getInstance().getPlacedBlocks().put(this, blocks);
	}
	
	public void resetPlacedBlocks() {
		this.getPlacedBlocks().forEach(b -> {
			b.getBlock().setType(Material.AIR);
		});
		SkyBridge.getInstance().getPlacedBlocks().remove(this);
	}
	
	public List<Block> getRedCageBlocks() {

		double x1 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+getWorld().getName()+".redcagepos1X");
		double y1 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+getWorld().getName()+".redcagepos1Y");
		double z1 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+getWorld().getName()+".redcagepos1Z");
		double x2 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+getWorld().getName()+".redcagepos2X");
		double y2 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+getWorld().getName()+".redcagepos2Y");
		double z2 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+getWorld().getName()+".redcagepos2Z");
		
		Location redpos1 = new Location(getWorld(), x1, y1, z1);
		Location redpos2 = new Location(getWorld(), x2, y2, z2);
		
		List<Block> blocks = Utils.getBlocks(redpos1, redpos2);
		
		return blocks;
	}
	
	public List<Block> getBlueCageBlocks() {

		double x1 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+getWorld().getName()+".bluecagepos1X");
		double y1 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+getWorld().getName()+".bluecagepos1Y");
		double z1 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+getWorld().getName()+".bluecagepos1Z");
		double x2 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+getWorld().getName()+".bluecagepos2X");
		double y2 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+getWorld().getName()+".bluecagepos2Y");
		double z2 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+getWorld().getName()+".bluecagepos2Z");
		
		Location bluepos1 = new Location(getWorld(), x1, y1, z1);
		Location bluepos2 = new Location(getWorld(), x2, y2, z2);
		
		List<Block> blocks = Utils.getBlocks(bluepos1, bluepos2);
		
		return blocks;
	}
	
	public List<Integer> getGoals() {
		List<Integer> goals = new ArrayList<>();
		int redgoals = 0;
		int bluegoals = 0;
		
		Collection<Hologram> holos = HologramAPI.getHolograms();
		for(Hologram h : holos) {
			if(h.getLocation().getWorld().equals(world)) {
				if(h.getText().contains("Rossa")) {
				if(h.getText().contains("1")) {
					redgoals = 1;
				} else if(h.getText().contains("2")) {
					redgoals = 2;
				} else if(h.getText().contains("3")) {
					redgoals = 3;
				} else if(h.getText().contains("4")) {
					redgoals = 4;
				} else if(h.getText().contains("5")) {
					redgoals = 5;
				}
				} else if(h.getText().contains("Blu")) {
					if(h.getText().contains("1")) {
						bluegoals = 1;
					} else if(h.getText().contains("2")) {
						bluegoals = 2;
					} else if(h.getText().contains("3")) {
						bluegoals = 3;
					} else if(h.getText().contains("4")) {
						bluegoals = 4;
					} else if(h.getText().contains("5")) {
						bluegoals = 5;
					}
				}
			}
		}
		
		goals.add(redgoals);
		goals.add(bluegoals);
		
		return goals;
	}
	
	public Type getType() { return type; }
}
