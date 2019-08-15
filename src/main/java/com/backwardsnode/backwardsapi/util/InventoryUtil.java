package com.backwardsnode.backwardsapi.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * A class for managing player inventories and custom inventory displays
 *
 */
public class InventoryUtil {

	/**
	 * Opens a basic player inventory containing given items with a inventory custom name
	 * @param player The player who should view the inventory
	 * @param name The (formatted) name of the inventory to create
	 * @param items The items to appear in the inventory
	 */
	public static void showInventory(Player player, String name, ItemStack... items) {
		Inventory i = Bukkit.createInventory(null, getPreferredSize(items.length), ChatColor.BLUE + "[SurvivalTools+] " + name);
		i.addItem(items);
		player.openInventory(i);
	}
	
	/**
	 * Finds the most appropriate multiple of 9 to set as the inventory size for a basic player inventory
	 * The number returned will be the same or a greater multiple of 9
	 * @param itemLength The number of items given, not necessarily a multiple of 9
	 * @return A multiple of 9 large enough to generate an inventory container for all of the items
	 */
	public static int getPreferredSize(int itemLength) {
		int put = itemLength % 9;
		put = itemLength - put;
		put = put == 0 ? 9 : put;
		put = put < itemLength ? put + 9 : put;
		return put;
	}
}
