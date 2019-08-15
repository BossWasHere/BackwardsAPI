package com.backwardsnode.backwardsapi;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import com.backwardsnode.backwardsapi.command.ICommandCoolable;

/**
 * A class for managing timeouts and cooldowns between events
 *
 */
public class CooldownManager {

	private final Map<String, Long> _cooldowns;

	/***
	 * Initializes a new {@link CooldownManager}
	 */
	public CooldownManager() {
		_cooldowns = new HashMap<String, Long>();
	}

	/**
	 * Adds a player activity cooldown which can be queried while the action is still in cooldown
	 * 
	 * @param player   The player who to add a cooldown timer for
	 * @param taskID   The referral name of the cooldown
	 * @param cooldown The time (in milliseconds) from now before the cooldown expires
	 */
	public void addCooldown(Player player, String taskID, long cooldown) {
		long expiry = new Date().getTime() + cooldown;
		_cooldowns.put(player.getName() + "/" + taskID, expiry);
	}

	/**
	 * Checks if an activity cooldown has expired yet
	 * 
	 * @param player The player who was used for the cooldown
	 * @param taskID The referral name of the cooldown
	 * @return The time until the cooldown expires, or 0 if the cooldown doesn't exist
	 */
	public long checkCooldown(Player player, String taskID) {
		try {
			String key = player.getName() + "/" + taskID;
			long period = _cooldowns.get(key).longValue() - new Date().getTime();
			if (period < 1) {
				_cooldowns.remove(key);
			}
			return period;
		} catch (NullPointerException e) {
			return 0;
		}
	}
	
	public ICommandCoolable attach(String taskID) {
		CooldownManager cdm = this;
		return new ICommandCoolable() {
			
			@Override
			public boolean isPlayerInCooldown(Player player) {
				return cdm.checkCooldown(player, taskID) > 1;
			}
			
			@Override
			public void addPlayerCooldown(Player player, long cooldown) {
				addCooldown(player, taskID, cooldown);
			}
		};
	}
}
