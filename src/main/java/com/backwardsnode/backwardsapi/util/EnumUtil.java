package com.backwardsnode.backwardsapi.util;

import java.util.Random;

/**
 * A class for managing and interfacing with enumerable objects and classes
 *
 */
public class EnumUtil {

	/**
	 * Selects a random enum constant from a given enum class
	 * @param enumeration The enum class to target
	 * @return A pseudorandom constant from the given class
	 */
	public static <T extends Enum<?>> T getRandomConstant(Class<T> enumeration) {
		int r = new Random().nextInt(enumeration.getEnumConstants().length);
		return enumeration.getEnumConstants()[r];
	}
}
