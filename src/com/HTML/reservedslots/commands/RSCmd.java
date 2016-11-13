package com.HTML.reservedslots.commands;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.HTML.reservedslots.RS;

public class RSCmd implements CommandExecutor {

	private RS main;

	public RSCmd(RS pl) {
		main = pl;
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String label, String[] args) {

		if (!(s instanceof Player)) {
			s.sendMessage("You must be a player to do this command!");
			return true;
		}

		Player p = (Player) s;

		if (c.getName().equalsIgnoreCase("reservedslots")) {

			if (args.length == 0) {
				p.sendMessage(col("&c&m------------- &3 ReservedSlots &c&m-------------"));
				p.sendMessage(
						col("&7ReservedSlots running version &a" + main.getDescription().getVersion() + "&7 by HTML"));
				p.sendMessage(col("&7Plugin commands - /rs help!"));
				p.sendMessage(col("&c&m------------------------------------"));

				return true;
			} else if (args.length == 1) {

				if (args[0].equalsIgnoreCase("reload") && p.hasPermission("reservedslots.admin")) {
					main.reloadConfig();
					p.sendMessage(col("&aConfig was reloaded sucesfully!"));
					return true;
				}

				if (args[0].equalsIgnoreCase("help") && p.hasPermission("reservedslots.admin")) {
					p.sendMessage(col("&c&m------------- &3 ReservedSlots Help Page &c&m-------------"));
					p.sendMessage(
							col("&7/rs set kickmessage <kick message> - Message a player gets when server is full."));
					p.sendMessage(col("&7/rs set reservedslots <reserved slots> - Sets reserved slots."));
					p.sendMessage(col("&7/rs set maxplayers <max players> - Sets max players."));
					p.sendMessage(col("&7/rs enable - Activates the plugin."));
					p.sendMessage(col("&7/rs disable - Disables the plugin."));
					p.sendMessage(col("&7/rs reload - Reloads config."));
					p.sendMessage(col("&c&m-----------------------------------------------"));
					return true;
				}

				if (args[0].equalsIgnoreCase("enable") && p.hasPermission("reservedslots.admin")) {
					main.configFile.set("PluginActive", true);
					p.sendMessage(col("&aPlugin enabled!"));
					return true;
				}

				if (args[0].equalsIgnoreCase("disable") && p.hasPermission("reservedslots.admin")) {
					main.configFile.set("PluginActive", false);
					p.sendMessage(col("&cPlugin disabled!"));
					return true;
				}

			} else if (args.length >= 3) {

				if (args[1].equalsIgnoreCase("maxplayers")) {
					main.configFile.set("MaxPlayers", Integer.parseInt(args[2]));
					p.sendMessage(col("&3Max player slots set to &a" + args[2]));
					try {
						main.configFile.save(main.ConfigFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
					main.reloadConfig();
					return true;

				} else if (args[1].equalsIgnoreCase("reservedslots")) {
					main.configFile.set("ReservedSlots", Integer.parseInt(args[2]));
					p.sendMessage(col("&3Reserved slots set to &a" + args[2]));
					try {
						main.configFile.save(main.ConfigFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
					main.reloadConfig();
					return true;

				} else if (args[1].equalsIgnoreCase("kickmessage")) {

					StringBuilder sb = new StringBuilder();
					for (int i = 2; i < args.length; ++i) {
						sb.append(args[i]).append(" ");
					}

					String msg = sb.toString().trim();
					String kickMessage = ChatColor.translateAlternateColorCodes('&', msg);
					main.configFile.set("ServerFullKickMessage", kickMessage.toString());
					p.sendMessage(col("&3Server full kick message set to &a" + kickMessage + "&a"));
					try {
						main.configFile.save(main.ConfigFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
					main.reloadConfig();
					return true;
				}
			}
		}
		return false;
	}

	private String col(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}

}