package com.backwardsnode.backwardsapi.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandModel {

	public static final CommandModel GENERIC = new CommandModel(ExecutingEntityType.ANY, ExecutionTargetType.NONE);
	public static final CommandModel PLAYER_ONLY_SELF = new CommandModel(ExecutingEntityType.PLAYER, ExecutionTargetType.PLAYER_SELF);
	public static final CommandModel PLAYER_OR_OTHER = new CommandModel(ExecutingEntityType.ANY, ExecutionTargetType.PLAYER_ANY);
	public static final CommandModel PLAYER_OR_OTHER_NO_CONSOLE = new CommandModel(ExecutingEntityType.PLAYER, ExecutionTargetType.PLAYER_ANY);
	public static final CommandModel OTHER_PLAYER = new CommandModel(ExecutingEntityType.ANY, ExecutionTargetType.PLAYER_OTHER);
	public static final CommandModel OTHER_PLAYER_NO_CONSOLE = new CommandModel(ExecutingEntityType.PLAYER, ExecutionTargetType.PLAYER_OTHER);
	public static final CommandModel ANY_CONSOLE = new CommandModel(ExecutingEntityType.ANY, ExecutionTargetType.CONSOLE);
	public static final CommandModel CONSOLE_ONLY = new CommandModel(ExecutingEntityType.CONSOLE, ExecutionTargetType.CONSOLE);
	
	private final ExecutingEntityType entityExecutor; 
	private final ExecutionTargetType entityTarget;
	private final PermissionRequirements permissions;
	
	private final ICommandCoolable cooldownSet;
	
	public CommandModel(ExecutingEntityType entityExecutor, ExecutionTargetType entityTarget) {
		this.entityExecutor = entityExecutor;
		this.entityTarget = entityTarget;
		this.permissions = PermissionRequirements.DEFAULT;
		this.cooldownSet = null;
	}
	
	public CommandModel(ExecutingEntityType entityExecutor, ExecutionTargetType entityTarget, PermissionRequirements permissions, ICommandCoolable cooldownSet) {
		this.entityExecutor = entityExecutor;
		this.entityTarget = entityTarget;
		this.permissions = permissions;
		this.cooldownSet = cooldownSet;
	}
	
	public static CommandModel asCoolableModel(CommandModel base, ICommandCoolable cooldownSet) {
		return new CommandModel(base.entityExecutor, base.entityTarget, base.permissions, cooldownSet);
	}
	
	public static CommandModel withPermissionRequirements(CommandModel base, PermissionRequirements permissions) {
		return new CommandModel(base.entityExecutor, base.entityTarget, permissions, base.cooldownSet);
	}
	
	public boolean forEntityExecution(CommandSender sender) {
		if (entityExecutor.equals(ExecutingEntityType.ANY)) {
			return true;
		}
		if (sender instanceof Player) {
			return entityExecutor.equals(ExecutingEntityType.PLAYER);
		}
		return entityExecutor.equals(ExecutingEntityType.CONSOLE);
	}
	
	public ExecutingEntityType getExecutingEntityType() {
		return entityExecutor;
	}
	
	public ICommandCoolable getCooldownState() {
		return cooldownSet;
	}
	
	public ExecutionState canExecute(CommandSender sender, String permissionCommandPrefix, String[] args) {
		return canExecute(sender, null, permissionCommandPrefix, args);
	}
	
	public ExecutionState canExecute(CommandSender sender, String overridePermission, String permissionCommandPrefix, String[] args) {
		if (!forEntityExecution(sender)) {
			return ExecutionState.NOT_FOR_EXECUTION;
		}
		if (cooldownSet != null && sender instanceof Player) {
			Player playerCooldown = (Player) sender;
			if (!hasCooldownBypass(playerCooldown, permissionCommandPrefix)) {
				if (cooldownSet.isPlayerInCooldown(playerCooldown)) {
					return ExecutionState.IN_COOLDOWN;
				}
			}
		}
		if (permissions.equals(PermissionRequirements.NONE)) {
			return ExecutionState.SUCCESS;
		}
		switch (entityTarget) {
		case PLAYER_SELF:
		case NONE:
			return hasValidPermission(sender, permissionCommandPrefix, overridePermission) ? ExecutionState.SUCCESS : ExecutionState.NO_PERMISSION;
		case PLAYER_ANY:
			if (args.length == 0) {
				return hasValidPermission(sender, permissionCommandPrefix, overridePermission) ? ExecutionState.SUCCESS : ExecutionState.NO_PERMISSION;
			}
			//No break point
		case PLAYER_OTHER:
			boolean validArgs = true;
			if (args.length < 1) {
				validArgs = false;
			}
			return hasValidPermission(sender, permissionCommandPrefix + ".other", overridePermission) ? ExecutionState.SUCCESS : validArgs ? ExecutionState.NO_PERMISSION : ExecutionState.INVALID_ARGS;
		case CONSOLE:
			return hasValidPermission(sender, permissionCommandPrefix + ".console", overridePermission) ? ExecutionState.SUCCESS : ExecutionState.NO_PERMISSION;
		}
		return ExecutionState.NO_PERMISSION;
	}
	
	public boolean hasCooldownBypass(Player player, String permissionPrefix) {
		return player.hasPermission(permissionPrefix + ".nocooldown");
	}
	
	public boolean hasValidPermission(CommandSender sender, String permission, String overridePermission) {
		if (sender.hasPermission(permission)) {
			return true;
		}
		if (overridePermission != null) {
			return sender.hasPermission(overridePermission);
		}
		return false;
	}
	
	public static enum ExecutionState {
		SUCCESS,
		NO_PERMISSION,
		NOT_FOR_EXECUTION,
		INVALID_ARGS,
		IN_COOLDOWN
	}
	
	public static enum ExecutingEntityType {
		PLAYER("players"),
		CONSOLE("console"),
		ANY(null);
		
		public final String tag;
		
		ExecutingEntityType(String tag) {
			this.tag = tag;
		}
	}

	public static enum ExecutionTargetType {
		PLAYER_SELF,
		PLAYER_OTHER,
		PLAYER_ANY,
		CONSOLE,
		NONE
	}

	public static enum PermissionRequirements {
		DEFAULT,
		NONE
	}
}
