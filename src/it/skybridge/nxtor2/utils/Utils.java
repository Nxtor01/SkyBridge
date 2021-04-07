package it.skybridge.nxtor2.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

import it.skybridge.nxtor2.SkyBridge;
import it.skybridge.nxtor2.objects.Arena;
import it.skybridge.nxtor2.objects.Team;
import it.skybridge.nxtor2.objects.User;
import net.md_5.bungee.api.ChatColor;

@SuppressWarnings("deprecation")
public class Utils {

	public static void teleportToMap(User u, Color c) {
		Arena a = u.getArena();
		FileConfiguration fc = SkyBridge.getInstance().getFilesManager().getArenasFile();
		String name = a.getWorld().getName();
        double x1 = fc.getDouble("arenas."+name+".x1");
        double y1 = fc.getDouble("arenas."+name+".y1");
        double z1 = fc.getDouble("arenas."+name+".z1");
        float yaw1 = (float) fc.getDouble("arenas."+name+".yaw1");
        float pitch1 = (float) fc.getDouble("arenas."+name+".pitch1");
        double x2 = fc.getDouble("arenas."+name+".x2");
        double y2 = fc.getDouble("arenas."+name+".y2");
        double z2 = fc.getDouble("arenas."+name+".z2");
        float yaw2 = (float) fc.getDouble("arenas."+name+".yaw2");
        float pitch2 = (float) fc.getDouble("arenas."+name+".pitch2");
        if(c.equals(Color.RED)) {
        u.teleport(new Location(a.getWorld(), x1, y1, z1, yaw1, pitch1));
        } else if(c.equals(Color.BLUE)) {
        u.teleport(new Location(a.getWorld(), x2, y2, z2, yaw2, pitch2));
        }
	}
	
	public static void teleportToUnderMap(User u, Color c, Arena a) {
		FileConfiguration fc = SkyBridge.getInstance().getFilesManager().getArenasFile();
		String name = a.getWorld().getName();
        double x1 = fc.getDouble("arenas."+name+".ux1");
        double y1 = fc.getDouble("arenas."+name+".uy1");
        double z1 = fc.getDouble("arenas."+name+".uz1");
        float yaw1 = (float) fc.getDouble("arenas."+name+".uyaw1");
        float pitch1 = (float) fc.getDouble("arenas."+name+".upitch1");
        double x2 = fc.getDouble("arenas."+name+".ux2");
        double y2 = fc.getDouble("arenas."+name+".uy2");
        double z2 = fc.getDouble("arenas."+name+".uz2");
        float yaw2 = (float) fc.getDouble("arenas."+name+".uyaw2");
        float pitch2 = (float) fc.getDouble("arenas."+name+".upitch2");
        if(c.equals(Color.RED)) {
        u.teleport(new Location(a.getWorld(), x1, y1, z1, yaw1, pitch1));
        } else if(c.equals(Color.BLUE)) {
        u.teleport(new Location(a.getWorld(), x2, y2, z2, yaw2, pitch2));
        }
	}
	
	public static void spawnInventory(User u) {
		u.clearInv();
		
		ItemStack is = new ItemStack(Material.BOOK);
		ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
		
		ItemMeta im = is.getItemMeta();
		ItemMeta sm = sword.getItemMeta();
		
		im.setDisplayName("§eLe tue statistiche");
		im.setLore(Arrays.asList("§7Tasto destro per vedere","§7le tue statistiche"));

		sm.setDisplayName("§eGioca");
		sm.setLore(Arrays.asList("§7Tasto destro per giocare"));
		
		is.setItemMeta(im);
		sword.setItemMeta(sm);
		
		u.getInv().setItem(0, is);
		u.getInv().setItem(4, sword);
	}
	
	public static void inventory(User u, Color color) {
		u.clearInv();
		
		ItemStack h = new ItemStack(Material.LEATHER_HELMET);
		ItemStack c = new ItemStack(Material.LEATHER_CHESTPLATE);
		ItemStack l = new ItemStack(Material.LEATHER_LEGGINGS);
		ItemStack b = new ItemStack(Material.LEATHER_BOOTS);
		
		LeatherArmorMeta hm = (LeatherArmorMeta) h.getItemMeta();
		LeatherArmorMeta cm = (LeatherArmorMeta) c.getItemMeta();
		LeatherArmorMeta lm = (LeatherArmorMeta) l.getItemMeta();
		LeatherArmorMeta bm = (LeatherArmorMeta) b.getItemMeta();
		
		hm.setColor(color);
		cm.setColor(color);
		lm.setColor(color);
		bm.setColor(color);

		hm.setDisplayName("§eElmo");
		cm.setDisplayName("§eCorazza");
		lm.setDisplayName("§eGambali");
		bm.setDisplayName("§eStivali");
		
		h.setItemMeta(hm);
		c.setItemMeta(cm);
		l.setItemMeta(lm);
		b.setItemMeta(bm);
		
		u.getInv().setHelmet(h);
		u.getInv().setChestplate(c);
		u.getInv().setLeggings(l);
		u.getInv().setBoots(b);
		
		ItemStack sword = new ItemStack(Material.IRON_SWORD);
		ItemStack bow = new ItemStack(Material.BOW);
		ItemStack pick = new ItemStack(Material.DIAMOND_PICKAXE);
		ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE, 5);
		ItemStack arrow = new ItemStack(Material.ARROW);
		ItemStack blocks;
		if(color.equals(Color.BLUE)) {
		 blocks = new ItemStack(Material.HARD_CLAY, 64, DyeColor.BLUE.getData());
		} else {
		 blocks = new ItemStack(Material.HARD_CLAY, 64, DyeColor.RED.getData());
		}
		ItemStack stick = new ItemStack(Material.STICK);
		
		pick.addEnchantment(Enchantment.DIG_SPEED, 3);
		stick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
		
		ItemMeta swordm = sword.getItemMeta();
		ItemMeta bowm = bow.getItemMeta();
		ItemMeta pickm = pick.getItemMeta();
		ItemMeta gapplem = gapple.getItemMeta();
		ItemMeta blocksm = blocks.getItemMeta();
		ItemMeta stickm = stick.getItemMeta();
		ItemMeta arrowm = arrow.getItemMeta();
		
		swordm.setDisplayName("§9Spada di ferro");
		bowm.setDisplayName("§9Arco");
		pickm.setDisplayName("§9Piccone di diamante");
		gapplem.setDisplayName("§9Mele d'oro");
		blocksm.setDisplayName("§9Blocchi");
		stickm.setDisplayName("§9Bastoncino");
		arrowm.setDisplayName("§9Freccia");
		
		sword.setItemMeta(swordm);
		bow.setItemMeta(bowm);
		pick.setItemMeta(pickm);
		gapple.setItemMeta(gapplem);
		blocks.setItemMeta(blocksm);
		stick.setItemMeta(stickm);
		arrow.setItemMeta(arrowm);
		
		u.getInv().setItem(0, sword);
		u.getInv().setItem(1, bow);
		u.getInv().setItem(2, pick);
		u.getInv().setItem(3, gapple);
		u.getInv().setItem(4, blocks);
		u.getInv().setItem(5, blocks);
		u.getInv().setItem(7, stick);
		u.getInv().setItem(8, arrow);
	}
	
	public static Location getLocationSpawn() {
		SkyBridge instance = SkyBridge.getInstance();
		double y = instance.getConfig().getDouble("spawnY");
		if(y == 200) {
			return new Location(Bukkit.getWorld("world"), 0.5, 100, 0.5);
		}
		double z = instance.getConfig().getDouble("spawnZ");
		double x = instance.getConfig().getDouble("spawnX");
		World w = Bukkit.getWorld(instance.getConfig().getString("spawnWorld"));
		float pitch = (float) instance.getConfig().getDouble("spawnPitch");
		float yaw = (float) instance.getConfig().getDouble("spawnYaw");
		
		Location l = new Location(w, x, y, z, yaw, pitch);
		return l;
	}
	
	public static void setLobby(User u) throws Exception {
		FileConfiguration f = SkyBridge.getInstance().getConfig();
		double x = u.getLoc().getX();
		double y = u.getLoc().getY();
		double z = u.getLoc().getZ();
		float pitch = u.getLoc().getPitch();
		float yaw = u.getLoc().getYaw();
		f.set("spawnWorld", u.getWorld().getName());
		f.set("spawnX", x);
		f.set("spawnY", y);
		f.set("spawnZ", z);
		f.set("spawnPitch", pitch);
		f.set("spawnYaw", yaw);
		SkyBridge.getInstance().saveConfig();
	}
	
	public static void joinInventory(User u) {
		u.clearInv();
		
		ItemStack is = new ItemStack(Material.RED_ROSE);
		ItemMeta im = is.getItemMeta();
		
		im.setDisplayName("§cEsci");
		
		im.setLore(Arrays.asList("§7Clicca col tasto destro","§7per uscire"));
		
		is.setItemMeta(im);
		
		u.getInv().setItem(8, is);
	}
	
	public static void fireworks(Location l) {
		Firework firework = (Firework) l.getWorld().spawnEntity(l, EntityType.FIREWORK);
		FireworkMeta fwm = firework.getFireworkMeta();
		
		fwm.setPower(2);
		
		firework.setFireworkMeta(fwm);

		new BukkitRunnable() {
			int times = 0;
			@Override
			public void run() {
				times++;
				firework.detonate();
				
				if(times >= 9) {
					this.cancel();
				}
			}
		}.runTaskTimer(SkyBridge.getInstance(), 5L, 5L);
	}
	
	public static void createCages(Arena a) {
		/*new BukkitRunnable() {
			@Override
			public void run() {
				double x1 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+a.getWorld().getName()+".redcagepos1X");
				double y1 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+a.getWorld().getName()+".redcagepos1Y");
				double z1 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+a.getWorld().getName()+".redcagepos1Z");
				double x2 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+a.getWorld().getName()+".bluecagepos1X");
				double y2 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+a.getWorld().getName()+".bluecagepos1Y");
				double z2 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+a.getWorld().getName()+".bluecagepos1Z");
				
				double x12 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+a.getWorld().getName()+".redcagepos2X");
				double y12 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+a.getWorld().getName()+".redcagepos2Y");
				double z12 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+a.getWorld().getName()+".redcagepos2Z");
				double x22 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+a.getWorld().getName()+".bluecagepos2X");
				double y22 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+a.getWorld().getName()+".bluecagepos2Y");
				double z22 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+a.getWorld().getName()+".bluecagepos2Z");
				
				Location redpos1 = new Location(a.getWorld(), x1, y1, z1);
				Location redpos2 = new Location(a.getWorld(), x12, y12, z12);
				Location bluepos1 = new Location(a.getWorld(), x2, y2, z2);
				Location bluepos2 = new Location(a.getWorld(), x22, y22, z22);
				
				CuboidSelection cis = new CuboidSelection(a.getWorld(), redpos1, redpos2);
				
				
				
			}
		}.runTaskAsynchronously(SkyBridge.getInstance());
		*/
	}
	
	private static void destroyCages(Arena a) {
		a.getRedCageBlocks().forEach(b -> b.setType(Material.AIR));
		a.getBlueCageBlocks().forEach(b -> b.setType(Material.AIR));
	}
	
	public static void runCage(Arena a, User scorer) {
		Team red = a.getTeamRed();
		Team blue = a.getTeamBlue();
		
		if(scorer != null) {
			red.sendTitle(scorer.getName()+" ha segnato!", ChatColor.GRAY);
			blue.sendTitle(scorer.getName()+" ha segnato!", ChatColor.GRAY);
		}
		
		createCages(a);
		
		for(User u : red.getTeam()) {
			teleportToMap(u, Color.RED);
			inventory(u, Color.RED);
		}
		
		for(User u : blue.getTeam()) {
			teleportToMap(u, Color.BLUE);
			inventory(u, Color.BLUE);
		}
		
		new BukkitRunnable() {
			@Override
			public void run() {
				red.sendTitle("5", ChatColor.DARK_GREEN);
				blue.sendTitle("5", ChatColor.DARK_GREEN);
			}
		}.runTaskLater(SkyBridge.getInstance(), 20);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				red.sendTitle("4", ChatColor.GREEN);
				blue.sendTitle("4", ChatColor.GREEN);
			}
		}.runTaskLater(SkyBridge.getInstance(), 40);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				red.sendTitle("3", ChatColor.YELLOW);
				blue.sendTitle("3", ChatColor.YELLOW);
			}
		}.runTaskLater(SkyBridge.getInstance(), 60);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				red.sendTitle("2", ChatColor.RED);
				blue.sendTitle("2", ChatColor.RED);
			}
		}.runTaskLater(SkyBridge.getInstance(), 80);
		new BukkitRunnable() {
			@Override
			public void run() {
				red.sendTitle("1", ChatColor.DARK_RED);
				blue.sendTitle("1", ChatColor.DARK_RED);
			}
		}.runTaskLater(SkyBridge.getInstance(), 100);
		new BukkitRunnable() {
			@Override
			public void run() {
				destroyCages(a);
				red.sendTitle("Via!", ChatColor.AQUA);
				blue.sendTitle("Via!", ChatColor.AQUA);
			}
		}.runTaskLater(SkyBridge.getInstance(), 120);
	}
	
	public static List<Block> getBlocks(Location pos1, Location pos2) {
		if(pos1.getWorld() != pos2.getWorld())
			return null;
		
		World w = pos1.getWorld();
		List<Block> blocks = new ArrayList<>();
		int x1 = pos1.getBlockX();
		int y1 = pos1.getBlockY();
		int z1 = pos1.getBlockZ();

		int x2 = pos2.getBlockX();
		int y2 = pos2.getBlockY();
		int z2 = pos2.getBlockZ();
		
		int lowestX = Math.min(x1, x2);
		int lowestY = Math.min(y1, y2);
		int lowestZ = Math.min(z1, z2);
		
		int highestX = lowestX == x1 ? x2 : x1;
		int highestY = lowestY == y1 ? y2 : y1;
		int highestZ = lowestZ == z1 ? z2 : z1;
		
		for(int x = lowestX; x <= highestX; x++)
			for(int y = lowestY; y <= highestY; y++)
				for(int z = lowestZ; z <= highestZ; z++)
					blocks.add(w.getBlockAt(x, y, z));
		return blocks;
	}
	
	public static ChatColor convertColor(Color c) {
		if(c.equals(Color.BLUE)) {
			return ChatColor.BLUE;
		} else if(c.equals(Color.RED)) {
			return ChatColor.RED;
		} else {
			return ChatColor.AQUA;
		}
	}
}
