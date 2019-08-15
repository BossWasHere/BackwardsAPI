package com.backwardsnode.backwardsapi.register;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

/**
 * A class for holding player names in a public list. Not thread-safe.
 *
 */
public class SimplePlayerRegistry {

	private List<String> nameList;
	private boolean storeUUIDs;

	/**
	 * Initializes a new instance of the registry list
	 */
	public SimplePlayerRegistry() {
		nameList = new ArrayList<String>();
		storeUUIDs = false;
	}
	
	/**
	 * Initializes a new instance of the registry list
	 * @param storeUUIDs If UUIDs should be stored instead of Player names
	 */
	public SimplePlayerRegistry(boolean storeUUIDs) {
		nameList = new ArrayList<String>();
		this.storeUUIDs = storeUUIDs;
	}

	/**
	 * Adds a player to the list
	 * @param player The {@link Player} to add
	 * @return True if the player was added, false if not
	 */
	public boolean addPlayer(Player player) {
		if (storeUUIDs) {
			return addPlayer(player.getUniqueId().toString());
		}
		return addPlayer(player.getName());
	}

	/**
	 * Adds a player to the list
	 * @param player The player to add
	 * @return True if the player was added, false if not
	 */
	public boolean addPlayer(String player) {
		if (isPlayerAdded(player)) {
			return false;
		}
		nameList.add(player);
		return true;
	}
	
	/**
	 * Adds multiple players to the list
	 * @param players The {@link Player}s to add
	 */
	public void addAllPlayers(List<Player> players) {
		players.forEach(player -> addPlayer(player));
	}

	/**
	 * Adds multiple players to the list
	 * @param players The players to add
	 */
	public void addAllPlayerNames(List<String> players) {
		players.forEach(player -> addPlayer(player));
	}

	/**
	 * Checks if a player exists within the list
	 * @param player The {@link Player} to find
	 * @return True if the player is on the list, false if not
	 */
	public boolean isPlayerAdded(Player player) {
		if (storeUUIDs) {
			return isPlayerAdded(player.getUniqueId().toString());
		}
		return isPlayerAdded(player.getName());
	}

	/**
	 * Checks if a player exists within the list
	 * @param player The player to find
	 * @return True if the player is on the list, false if not
	 */
	public boolean isPlayerAdded(String player) {
		return nameList.contains(player);
	}

	/**
	 * Removes a player from the list
	 * @param player The {@link Player} to remove from the list
	 * @return True if the player was removed from the list, false if not
	 */
	public boolean removePlayer(Player player) {
		if (storeUUIDs) {
			return removePlayer(player.getUniqueId().toString());
		}
		return removePlayer(player.getName());
	}

	/**
	 * Removes a player from the list
	 * @param player The player to remove from the list
	 * @return True if the player was removed from the list, false if not
	 */
	public boolean removePlayer(String player) {
		if (isPlayerAdded(player)) {
			nameList.remove(player);
			return true;
		}
		return false;
	}
	
	public List<String> getAllPlayers() {
		return new ArrayList<String>(nameList);
	}

	/**
	 * Empties the list of players
	 */
	public void purge() {
		nameList.clear();
	}
}
