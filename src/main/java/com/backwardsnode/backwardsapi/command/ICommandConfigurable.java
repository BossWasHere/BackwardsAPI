package com.backwardsnode.backwardsapi.command;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * An interface representing an object that loads from a configuration file
 *
 */
public interface ICommandConfigurable {
	
	/**
	 * Loads the configuration for this object
	 * @param config The configuration data to use
	 */
	public void fetchConfig(FileConfiguration config);
}
