package com.backwardsnode.backwardsapi.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * A class for managing basic IO operations with files
 *
 */
public class FileUtil {

	/**
	 * Reads the entire contents from a file at a given path to a string
	 * @param path The path of the file to read from
	 * @param encoding The encoding of the file
	 * @return A string object containing the file's contents
	 * @throws IOException if the file cannot be read
	 */
	public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	
	/**
	 * Writes and overwrites a file with a given string input using default encoding
	 * @param path The path of the file to write to
	 * @param content The content to write to the file
	 * @return True if the file was successfully written to, false if not
	 */
	public static boolean writeFile(String path, String content) {
		try (PrintStream out = new PrintStream(new FileOutputStream(path))) {
		    out.print(content);
		    return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
}
