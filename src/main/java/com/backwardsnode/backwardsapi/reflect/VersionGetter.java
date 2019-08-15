package com.backwardsnode.backwardsapi.reflect;

import org.bukkit.Bukkit;

/**
 * A class for dynamically getting the version of the currently running server for reflection
 *
 */
public class VersionGetter {

	private static VersionGetter current;
	
	private String packageHeader;
	private String version;
	
	/**
	 * Gets or creates a new instance of VersionGetter
	 * @return The VersionGetter object
	 */
	public static VersionGetter get() {
		if (current == null) {
			current = new VersionGetter();
			current.packageHeader = Bukkit.getServer().getClass().getPackage().getName();
			current.version = current.packageHeader.substring(current.packageHeader.lastIndexOf(".") + 1);
		}
		return current;
	}
	
	/**
	 * Gets the package domain for org.bukkit classes
	 * @return The string header for the package org.bukkit
	 */
	public String bukkitPackageHeader() {
		return packageHeader;
	}
	
	/**
	 * Gets the active server version for NMS
	 * @return The string version of the current server instance
	 */
	public String version() {
		return version;
	}
	
	/**
	 * Gets the NMS package domain for net.minecraft.server classes
	 * @return The string header for NMS packages
	 */
	public String packageHeader() {
		return "net.minecraft.server." + version;
	}
	
	/**
	 * Gets the package domain for org.bukkit classes. Static method for easy calling
	 * @return The string header for the package org.bukkit
	 */
	public static String getBukkitPackageHeader() {
		return get().packageHeader;
	}
	
	/**
	 * Gets the active server version for NMS. Static method for easy calling
	 * @return The string version of the current server instance
	 */
	public static String getVersion() {
		return get().version;
	}
	
	/**
	 * Gets the NMS package domain for net.minecraft.server classes. Static method for easy calling
	 * @return The string header for NMS packages
	 */
	public static String getPackageHeader() {
		return "net.minecraft.server." + get().version;
	}
}
