package com.backwardsnode.backwardsapi.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.backwardsnode.backwardsapi.ILocalization;
import com.backwardsnode.backwardsapi.util.WorldUtil;

public abstract class PluginCommand extends Command implements IPluginCommand {

	private final CommandModel model;
	private final ILocalization baseLocale;
	private final TabCompleteMethod tabCompleteMethod;
	
	private String overridePermission;
	
	public PluginCommand(String command, List<String> aliases, CommandModel model, ILocalization baseLocale) {
		super(command, baseLocale.command_Description(command), "/" + command, aliases);
		this.model = model;
		this.baseLocale = baseLocale;
		this.tabCompleteMethod = TabCompleteMethod.NONE;
	}
	
	public PluginCommand(String command, List<String> aliases, CommandModel model, ILocalization baseLocale, TabCompleteMethod tabCompleteMethod) {
		super(command, baseLocale.command_Description(command), "/" + command, aliases);
		this.model = model;
		this.baseLocale = baseLocale;
		this.tabCompleteMethod = tabCompleteMethod;
	}
	
	public PluginCommand(String command, List<String> aliases, String forcedDescription, CommandModel model, ILocalization baseLocale) {
		super(command, forcedDescription, "/" + command, aliases);
		this.model = model;
		this.baseLocale = baseLocale;
		this.tabCompleteMethod = TabCompleteMethod.NONE;
	}
	
	public PluginCommand(String command, List<String> aliases, String forcedDescription, CommandModel model, ILocalization baseLocale, TabCompleteMethod tabCompleteMethod) {
		super(command, forcedDescription, "/" + command, aliases);
		this.model = model;
		this.baseLocale = baseLocale;
		this.tabCompleteMethod = tabCompleteMethod;
	}

	public void setOverridePermission(String overridePermission) {
		this.overridePermission = overridePermission;
	}
	
	@Override
	public final boolean execute(CommandSender sender, String commandLabel, String[] args) {
		String localeTarget = "";
		if (sender instanceof Player) {
			localeTarget = ((Player)sender).getLocale();
		}
		switch (model.canExecute(sender, overridePermission, getPluginPrefix() + "." + getName(), args)) {
		case SUCCESS:
			if (sender instanceof Player) {
				return executeCommand((Player) sender, args);
			} else {
				return executeCommand(sender, args);
			}
		case IN_COOLDOWN:
			sender.sendMessage(baseLocale.feedback_InCooldown(localeTarget));
			break;
		case INVALID_ARGS:
			sender.sendMessage(getUsage());
			break;
		case NO_PERMISSION:
			sender.sendMessage(baseLocale.feedback_NoPermission(localeTarget));
			break;
		case NOT_FOR_EXECUTION:
			sender.sendMessage(baseLocale.feedback_ExclusiveSender(localeTarget, model.getExecutingEntityType().tag));
			break;
		}
		return true;
	}
	
	public final void addCooldown(Player player, long cooldown) {
		model.getCooldownState().addPlayerCooldown(player, cooldown);
	}
	
	public boolean executeCommand(Player player, String[] args) {
		return true;
	}
	
	public boolean executeCommand(CommandSender sender, String[] args) {
		return true;
	}
	
	@Override
	public boolean testPermission(CommandSender target) {
		return model.forEntityExecution(target);
	}
	
	@Override
	public boolean testPermissionSilent(CommandSender target) {
		return testPermission(target);
	}
	
	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		return tabComplete(sender, alias, args, null);
	}
	
	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args, Location location) throws IllegalArgumentException {
		if (tabCompleteMethod.equals(TabCompleteMethod.ONLINE_PLAYERS)) {
			return WorldUtil.getOnlinePlayers();
		}
		return new ArrayList<String>();
	}
}
