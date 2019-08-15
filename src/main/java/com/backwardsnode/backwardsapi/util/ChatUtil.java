package com.backwardsnode.backwardsapi.util;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.entity.Player;

import com.backwardsnode.backwardsapi.reflect.VersionGetter;
import com.backwardsnode.backwardsapi.syntax.JsonMessage;
import com.backwardsnode.backwardsapi.syntax.JsonMessage.CompoundJsonMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * A class for building JSON messages in the format of Minecraft's chat output
 *
 */
public class ChatUtil {

	/**
	 * Sends a raw JSON message input string to the client of an online player
	 * @param player The target player entity
	 * @param message The raw JSON message to send
	 * @return True if the message was dispatched successfully, false if not
	 */
	public static boolean sendRawMessage(Player player, String message) {
		try {
			Class<?> packetClass = Class.forName(VersionGetter.getPackageHeader() + ".Packet");
			Class<?> packetChatClass = Class.forName(VersionGetter.getPackageHeader() + ".PacketPlayOutChat");
			Class<?> chatBaseClass = Class.forName(VersionGetter.getPackageHeader() + ".IChatBaseComponent");
			Class<?> chatSerializerClass = Class.forName(VersionGetter.getPackageHeader() + ".IChatBaseComponent$ChatSerializer");
			Class<?> craftPlayerClass = Class.forName(VersionGetter.getBukkitPackageHeader() + ".entity.CraftPlayer");
			Class<?> entityPlayerClass = Class.forName(VersionGetter.getPackageHeader() + ".EntityPlayer");
			Class<?> playerConnectionClass = Class.forName(VersionGetter.getPackageHeader() + ".PlayerConnection");
			
			Method jsonSerializer = chatSerializerClass.getMethod("a", String.class);
			Object outputMessage = jsonSerializer.invoke(null, message);
			
			Constructor<?> packetConstructor = packetChatClass.getDeclaredConstructor(chatBaseClass);
			Object packet = packetConstructor.newInstance(outputMessage);
			
			Object craftPlayer = craftPlayerClass.cast(player);
			Method getHandle = craftPlayerClass.getMethod("getHandle", new Class<?>[] {});
			Object handle = getHandle.invoke(craftPlayer, new Object[] {});
			Field playerConnectionField = entityPlayerClass.getField("playerConnection");
			playerConnectionField.setAccessible(true);
			Object playerConnection = playerConnectionField.get(handle);
			
			Method sendPacket = playerConnectionClass.getMethod("sendPacket", packetClass);
			sendPacket.invoke(playerConnection, packet);
			
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Sends a JsonMessage object to the client of an online player
	 * @param player The target player entity
	 * @param message The {@link JsonMessage} formatted object
	 * @return True if the message was dispatched successfully, false if not
	 */
	public static boolean sendJsonMessage(Player player, JsonMessage message) {
		checkNotNull(message);
		return sendRawMessage(player, buildRawMessage(message));
	}
	
	/**
	 * Sends multiple JsonMessage objects in a concatenated string to the client of an online player
	 * @param player The target player entity
	 * @param message The {@link CompoundJsonMessage} formatted object
	 * @return True if the message was dispatched successfully, false if not
	 */
	public static boolean sendJsonMessage(Player player, CompoundJsonMessage compoundMessage) {
		checkNotNull(compoundMessage);
		String construct = "[\"\"";
		for (JsonMessage msg : compoundMessage.messages) {
			construct += "," + buildRawMessage(msg);
		}
		construct += "]";
		return sendRawMessage(player, construct);
	}
	
	/**
	 * Transforms a JsonMessage into a usable JSON string. The equivalent of {@linkplain JsonMessage.toString()}.
	 * @param message The {@link JsonMessage} to transform
	 * @return The string value of the JsonMessage
	 */
	public static String buildRawMessage(JsonMessage message) {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		return gson.toJson(message);
	}
}