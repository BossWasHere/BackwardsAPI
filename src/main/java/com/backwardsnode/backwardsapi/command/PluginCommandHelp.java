package com.backwardsnode.backwardsapi.command;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.backwardsnode.backwardsapi.command.CommandModel.PermissionRequirements;

/**
 * A parameterized command class for providing help for plugin commands
 *
 */
public class PluginCommandHelp extends PluginCommand {

	private final Command[] pluginCommands;
	private int maxPages;
	private String helpHeader;
	private final String pluginName;
	
	/**
	 * Instantiates the helper command with a given name
	 * @param name The name of the command to register
	 * @param pluginName The name of the plugin to display when helping
	 * @param aliases Command aliases for the help command
	 * @param pluginCommands Array of the other commands registered by this plugin
	 */
	public PluginCommandHelp(String name, String pluginName, List<String> aliases, final Command[] pluginCommands) {
		super(name, aliases, "Shows help for " + pluginName, CommandModel.withPermissionRequirements(CommandModel.GENERIC, PermissionRequirements.NONE), null);
		this.pluginCommands = pluginCommands;
		this.pluginName = pluginName;
		helpHeader = ChatColor.GOLD + "[Showing help for " + ChatColor.BLUE + pluginName + ChatColor.GOLD + " Page: $page/$total]";
		maxPages();
	}
	
	/**
	 * Instantiates the helper command with a given name
	 * @param name The name of the command to register
	 * @param pluginName The name of the plugin to display when helping
	 * @param aliases Command aliases for the help command
	 * @param helpHeader The banner/header to show at the top every time the command is executed
	 * @param pluginCommands Array of the other commands registered by this plugin
	 * <p>Supports use of format vars $page and $total in <code>helpHeader</code></p>
	 */
	public PluginCommandHelp(String name, String pluginName, List<String> aliases, String helpHeader, final Command[] pluginCommands) {
		super(name, aliases, "Shows help for " + pluginName, CommandModel.withPermissionRequirements(CommandModel.GENERIC, PermissionRequirements.NONE), null);
		this.pluginCommands = pluginCommands;
		this.pluginName = pluginName;
		this.helpHeader = helpHeader;
		maxPages();
	}

	private void maxPages() {
		if (pluginCommands.length > 6) {
			maxPages = (int) Math.ceil(pluginCommands.length / 6);
		}
	}
	
	/**
	 * Called when a player executes the command
	 * @param player The player executing the command
	 * @param args The arguments passed with the command
	 * @return True if the command was executed successfully, false if not
	 */
	@Override
	public boolean executeCommand(Player player, String[] args) {
		return executeCommand((CommandSender)player, args);
	}
	
	/**
	 * Called when an entity executes the command
	 * @param sender The entity executing the command
	 * @param args The arguments passed with the command
	 * @return True if the command was executed successfully, false if not
	 */
	@Override
	public boolean executeCommand(CommandSender sender, String[] args) {
		int page = 1;		
		if (args.length > 0) {
			try {
				page = Integer.parseInt(args[0]);
			} catch (Exception e) { }
			if (page < 1) {
				page = 1;
			}
			if (page > maxPages) {
				page = maxPages;
			}
		}
		sender.sendMessage(helpHeader.replace("$page", page + "").replace("$total", maxPages + ""));
		short j = 0;
		for (int i = (page - 1) * 6; i < pluginCommands.length && j < 6; i++) {
			Command c = pluginCommands[i];
			sender.sendMessage(ChatColor.BLUE + "/" + c.getName() + " - " + ChatColor.DARK_AQUA + c.getDescription());
			j++;
		}
		return true;
	}
	
	@Override
	public String getPluginPrefix() {
		return pluginName;
	}

}
