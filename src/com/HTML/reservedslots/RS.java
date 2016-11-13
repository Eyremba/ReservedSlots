package com.HTML.reservedslots;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.HTML.reservedslots.commands.RSCmd;
import com.HTML.reservedslots.listeners.Join;
import com.HTML.reservedslots.listeners.PingEvent;

public class RS extends JavaPlugin {

	public File ConfigFile = new File(getDataFolder(), "config.yml");
	public FileConfiguration configFile = YamlConfiguration.loadConfiguration(ConfigFile);

	public void onEnable() {
		logger("&c----- &3Reserved Slots &c-----");
		logger("ReservedSlots running version: " + getDescription().getVersion());
		logger("ReservedSlots author: " + getDescription().getAuthors());
		this.registerCommands();
		this.registerEvents();
		checkName();
		getDataFolder().mkdirs();
		configFile.options().copyDefaults(true);
		try {
			configFile.save(ConfigFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void onDisable() {
		logger("&aReservedSlots disabled!");
	}

	private void registerEvents() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new PingEvent(this), this);
		pm.registerEvents(new Join(this), this);
	}

	public void createConfig() {
		configFile.createSection("MaxPlayers");
		configFile.set("MaxPlayers", 150);
		configFile.createSection("ReservedSlots");
		configFile.set("ReservedSlots", 25);
		configFile.createSection("ServerFullKickMessage");
		configFile.set("ServerFullKickMessage", "&cThe server is full, become a donor to bypass this!");
		configFile.addDefault("PluginActive", true);
	}

	private void registerCommands() {
		getCommand("reservedslots").setExecutor(new RSCmd(this));
	}

	public void logger(String text) {
		String string = ChatColor.translateAlternateColorCodes('&', text);
		Bukkit.getConsoleSender().sendMessage(string);
	}

   public void checkName() {
	   if(!(getDescription().getName().equalsIgnoreCase("ReservedSlots"))) {
		   getServer().getPluginManager().disablePlugin(this);
	   } else {
		   getServer().getPluginManager().enablePlugin(this);
	   }
   }

}
