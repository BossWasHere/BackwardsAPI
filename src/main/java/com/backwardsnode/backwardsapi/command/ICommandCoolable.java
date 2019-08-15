package com.backwardsnode.backwardsapi.command;

import org.bukkit.entity.Player;

/**
 * An interface representing an object that natively supports player action cooldowns
 *
 */
public interface ICommandCoolable {

	/**
	 * Checks if a player is currently in a cooldown state for this object
	 * @param player The player to check
	 * @return True if the player is in a cooldown state, false if not
	 */
	public boolean isPlayerInCooldown(Player player);
	
	/**
	 * Adds a player to the cooldown register
	 * @param player The player to add
	 * @param cooldown The duration before the player can execute the command again
	 */
	public void addPlayerCooldown(Player player, long cooldown);
}
