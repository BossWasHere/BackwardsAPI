package com.backwardsnode.backwardsapi.world;

import org.bukkit.util.EulerAngle;

/**
 * A class for converting between {@link EulerAngle}s and degrees
 *
 */
public class DegreeEulerAngle extends EulerAngle {

	/**
	 * Constructs EulerAngle child object from degrees
	 * @param x The x angle in degrees
	 * @param y The y angle in degrees
	 * @param z The z angle in degrees
	 */
	public DegreeEulerAngle(double x, double y, double z) {
		super(convertToRadians(x), convertToRadians(y), convertToRadians(z));
	}

	/**
	 * Converts the value of the X angle in degrees
	 * @return The angle in degrees
	 */
	public double getXDegrees() {
		return convertToDegrees(getX());
	}
	
	/**
	 * Converts the value of the Y angle in degrees
	 * @return The angle in degrees
	 */
	public double getYDegrees() {
		return convertToDegrees(getY());
	}
	
	/**
	 * Converts the value of the Z angle in degrees
	 * @return The angle in degrees
	 */
	public double getZDegrees() {
		return convertToDegrees(getZ());
	}
	
	/**
	 * Adds degrees to the EulerAngle format
	 * @param x The x angle in degrees
	 * @param y The y angle in degrees
	 * @param z The z angle in degrees
	 * @return The new EulerAngle with added degrees
	 */
	public EulerAngle addDegrees(double x, double y, double z) {
		return add(convertToRadians(x), convertToRadians(y), convertToRadians(z));
	}
	
	/**
	 * Subtracts degrees from the EulerAngle format
	 * @param x The x angle in degrees
	 * @param y The y angle in degrees
	 * @param z The z angle in degrees
	 * @return The new EulerAngle with subtracted degrees
	 */
	public EulerAngle subtractDegrees(double x, double y, double z) {
		return addDegrees(-x, -y, -z);
	}
	
	/**
	 * Sets the x angle
	 * @param x The x angle in degrees
	 * @return The new EulerAngle
	 */
	public EulerAngle setXDegrees(double x) {
		return setX(convertToRadians(x));
	}
	
	/**
	 * Sets the y angle
	 * @param y The y angle in degrees
	 * @return The new EulerAngle
	 */
	public EulerAngle setYDegrees(double y) {
		return setY(convertToRadians(y));
	}
	
	/**
	 * Sets the z angle
	 * @param z The z angle in degrees
	 * @return The new EulerAngle
	 */
	public EulerAngle setZDegrees(double z) {
		return setZ(convertToRadians(z));
	}
	
	/**
	 * Converts a value from degrees to radians used by the {@link EulerAngle} format
	 * @param deg The value to convert
	 * @return The same angle in radians
	 */
	public static double convertToRadians(double deg) {
		return deg * (Math.PI / 180); 
	}
	
	/**
	 * Converts a value from radians to degrees
	 * @param rad The value to convert
	 * @return The same angle in degrees
	 */
	public static double convertToDegrees(double rad) {
		return rad * (180 / Math.PI);
	}
}
