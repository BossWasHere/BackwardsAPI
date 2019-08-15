package com.backwardsnode.backwardsapi.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.backwardsnode.backwardsapi.world.AxisControl;

/**
 * A class for managing world and block activities
 *
 */
public class WorldUtil {
	
	/**
	 * Serializes a location object to a comma-separated string in the format:
	 * <blockquote>x,y,z,yaw,pitch,world</blockquote>
	 * @param location The location in the world to serialize
	 * @return A string representation of the location
	 */
	public static String stringFromLocation(Location location) {
		return String.format("%e,%e,%e,%e,%e,%s", location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), location.getWorld().getName());
	}

	/**
	 * Deserializes a comma-separated string in the format:
	 * <blockquote>x,y,z,yaw,pitch,world</blockquote>
	 * To a location within a loaded bukkit world
	 * @param location The string to deserialize
	 * @return A Location object representing the position of the string
	 */
	public static Location locationFromString(String location) {
		double x,y,z;
		float yaw, pitch;
		String name;
		String[] spl = location.split(",");
		try {
			x = Double.parseDouble(spl[0]);
			y = Double.parseDouble(spl[1]);
			z = Double.parseDouble(spl[2]);
			yaw = Float.parseFloat(spl[3]);
			pitch = Float.parseFloat(spl[4]);
			name = spl[5];
		} catch (NumberFormatException e) {
			return null;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
		World world = Bukkit.getWorld(name);
		if (world == null) return null;
		Location l = new Location(world, x, y, z, yaw, pitch);
		return l;
	}
	
	/**
	 * Finds the blocks directly adjacent to another block in the world
	 * @param block The central block to search from
	 * @param corners If the non-blockface corners should be included
	 * @return An array of the blocks found
	 */
	public static Block[] getBlockNeighbors(Block block, boolean corners) {
		Block[] neighbors = new Block[corners ? 14 : 6];
		neighbors[0] = block.getRelative(BlockFace.UP);
		neighbors[1] = block.getRelative(BlockFace.DOWN);
		neighbors[2] = block.getRelative(BlockFace.NORTH);
		neighbors[3] = block.getRelative(BlockFace.EAST);
		neighbors[4] = block.getRelative(BlockFace.SOUTH);
		neighbors[5] = block.getRelative(BlockFace.WEST);
		if (corners) {
			neighbors[6] = block.getRelative(0, 1, 1);
			neighbors[7] = block.getRelative(1, 1, 0);
			neighbors[8] = block.getRelative(0, 1, -1);
			neighbors[9] = block.getRelative(-1, 1, 0);
			neighbors[10] = block.getRelative(0, -1, 1);
			neighbors[11] = block.getRelative(1, -1, 0);
			neighbors[12] = block.getRelative(0, -1, -1);
			neighbors[13] = block.getRelative(-1, -1, 0);
		}
		return neighbors;
	}

	/**
	 * Finds a block with the specified material along the X axis
	 * @param b The block to start searching from
	 * @param m The target material to find
	 * @param c The direction along the axis to search in
	 * @param l The maximum distance from the start block
	 * @return The block matching the material, otherwise if not found returns null
	 */
	public static Block findAlongXAxis(Block block, Material m, AxisControl c, int l) {
		for (int i = 1; i < l; i++) {
			if (c == AxisControl.POSITIVE_ONLY || c == AxisControl.BIDIRECTIONAL) {
				if (block.getRelative(i, 0, 0).getType() == m) {
					return block.getRelative(i, 0, 0);
				}
			}
			if (c == AxisControl.NEGATIVE_ONLY || c == AxisControl.BIDIRECTIONAL) {
				if (block.getRelative(-i, 0, 0).getType() == m) {
					return block.getRelative(-i, 0, 0);
				}
			}
		}
		return null;
	}
	
	/**
	 * Finds a block with the specified material along the Z axis
	 * @param b The block to start searching from
	 * @param m The target material to find
	 * @param c The direction along the axis to search in
	 * @param l The maximum distance from the start block
	 * @return The block matching the material, otherwise if not found returns null
	 */
	public static Block findAlongZAxis(Block block, Material m, AxisControl c, int l) {
		for (int i = 1; i < l; i++) {
			if (c == AxisControl.POSITIVE_ONLY || c == AxisControl.BIDIRECTIONAL) {
				if (block.getRelative(0, 0, i).getType() == m) {
					return block.getRelative(0, 0, i);
				}
			}
			if (c == AxisControl.NEGATIVE_ONLY || c == AxisControl.BIDIRECTIONAL) {
				if (block.getRelative(0, 0, -i).getType() == m) {
					return block.getRelative(0, 0, -i);
				}
			}
		}
		return null;
	}
	
	public static List<String> getOnlinePlayers() {
		List<String> players = new ArrayList<String>();
		Bukkit.getOnlinePlayers().forEach(player -> players.add(player.getName()));
		return players;
	}
}
