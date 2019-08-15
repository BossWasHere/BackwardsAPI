package com.backwardsnode.backwardsapi.util;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

/**
 * A class for cryptographic utilities and hash functions
 *
 */
public class CryptoUtil {

	/**
	 * Runs a SHA1 hash on the input string using UTF-8
	 * <blockquote><b>This function is not deemed to be cryptographically safe, consider using SHA256</b></blockquote>
	 * @param input The input string
	 * @return The hash digest of the string
	 */
	public static String hashSha1(String input) {
		return Hashing.sha1().hashString(input, StandardCharsets.UTF_8).toString();
	}
	
	/**
	 * Runs a SHA256 hash on the input string using UTF-8
	 * @param input The input string
	 * @return The hash digest of the string
	 */
	public static String hashSha256(String input) {
		return Hashing.sha256().hashString(input, StandardCharsets.UTF_8).toString();
	}
	
	/**
	 * Runs a SHA384 hash on the input string using UTF-8
	 * @param input The input string
	 * @return The hash digest of the string
	 */
	public static String hashSha384(String input) {
		return Hashing.sha384().hashString(input, StandardCharsets.UTF_8).toString();
	}
	
	/**
	 * Runs a SHA512 hash on the input string using UTF-8
	 * @param input The input string
	 * @return The hash digest of the string
	 */
	public static String hashSha512(String input) {
		return Hashing.sha512().hashString(input, StandardCharsets.UTF_8).toString();
	}
	
	/**
	 * Runs a MD5 hash on the input string using UTF-8
	 * <blockquote><b>This function is not deemed to be cryptographically safe, consider using SHA256</b></blockquote>
	 * @param input The input string
	 * @return The hash digest of the string
	 */
	public static String hashMd5(String input) {
		return Hashing.md5().hashString(input, StandardCharsets.UTF_8).toString();
	}
}
