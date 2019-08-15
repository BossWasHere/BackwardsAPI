package com.backwardsnode.backwardsapi.util;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Arrays;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * A class for managing inventory items and {@link ItemStack}s
 *
 */
public class ItemUtil {

	/**
	 * Adds a custom display name and custom lore entries to the item metadata of a given {@link ItemStack}
	 * @param item The {@link ItemStack} to add the meta to
	 * @param name The custom display name to set
	 * @param lore The custom lore lines to set
	 */
	public static void addNameAndLore(ItemStack item, String name, String... lore) {
		ItemMeta itemMeta = item.getItemMeta();
		if (name != null) {
			itemMeta.setDisplayName(name);
		}
		if (lore != null) {
			itemMeta.setLore(Arrays.asList(lore));
		}
		item.setItemMeta(itemMeta);
	}
	
	/**
	 * Checks if an item metadata matches (on an existence level) a reference model
	 * @param item The {@link ItemStack} in question
	 * @param reference The {@link ItemStack} with meta to check the item against
	 * @return True if the items are equivalent (or better), false if not
	 */
	public static boolean matchesMetaAndType(ItemStack item, ItemStack reference) {
		if (item == null) {
			return false;
		}
		checkNotNull(reference);
		if (item.getType() != reference.getType()) {
			return false;
		}
		if (!item.hasItemMeta() || !reference.hasItemMeta()) {
			return false;
		}
		ItemMeta itemMeta = item.getItemMeta();
		ItemMeta referenceMeta = reference.getItemMeta();
		if (referenceMeta.hasDisplayName()) {
			if (itemMeta.hasDisplayName()) {
				if (!itemMeta.getDisplayName().equals(referenceMeta.getDisplayName())) {
					return false;
				}
			} else {
				return false;
			}
		}
		if (referenceMeta.hasLore()) {
			if (itemMeta.hasLore()) {
				if (itemMeta.getLore().size() != referenceMeta.getLore().size()) {
					return false;
				}
				for (int i = 0; i < itemMeta.getLore().size(); i++) {
					if (!itemMeta.getLore().get(i).equals(referenceMeta.getLore().get(i))) {
						return false;
					}
				}
			} else {
				return false;
			}
		}
		return true;
	}
}
