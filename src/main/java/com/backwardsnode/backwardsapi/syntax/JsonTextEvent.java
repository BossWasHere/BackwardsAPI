package com.backwardsnode.backwardsapi.syntax;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A class for serializing JSON message events
 *
 */
public class JsonTextEvent {

	/**
	 * The name of the action
	 */
	public String action;
	/**
	 * The value of the action
	 */
	public String value;
	
	/**
	 * Builds a new {@code JsonTextEvent} with a URL click event
	 * @param url The target URL
	 * @return The new JsonTextEvent object
	 */
	public static JsonTextEvent openURL(String url) {
		JsonTextEvent te = new JsonTextEvent();
		te.action = "open_url";
		te.value = url == null ? "" : url;
		return te;
	}
	
	/**
	 * Builds a new {@code JsonTextEvent} with a command click event
	 * @param command The command to execute
	 * @return The new JsonTextEvent object
	 */
	public static JsonTextEvent runCommand(String command) {
		JsonTextEvent te = new JsonTextEvent();
		te.action = "run_command";
		te.value = command == null ? "" : command;
		return te;
	}
	
	/**
	 * Builds a new {@code JsonTextEvent} with a show text hover event
	 * @param url The text to reveal
	 * @return The new JsonTextEvent object
	 */
	public static JsonTextEvent showText(String msg) {
		JsonTextEvent te = new JsonTextEvent();
		te.action = "show_text";
		te.value = msg == null ? "" : msg;
		return te;
	}
	
	/**
	 * Verifies if the given event is a click event
	 * @return True if the event is a click event, false if not
	 */
	public boolean isClickEvent() {
		return isClickEvent(this);
	}
	
	/**
	 * Verifies if the given event is a hover event
	 * @return True if the event is a hover event, false if not
	 */
	public boolean isHoverEvent() {
		return isHoverEvent(this);
	}
	
	/**
	 * Verifies if the given event is a click event
	 * @return True if the event is a click event, false if not
	 */
	public static final boolean isClickEvent(JsonTextEvent e) {
		checkNotNull(e);
		switch (e.action.toLowerCase()) {
		case "run_command":
		case "suggest_command":
		case "open_url":
		case "change_page":
			return true;
		}
		return false;
	}
	
	/**
	 * Verifies if the given event is a hover event
	 * @return True if the event is a hover event, false if not
	 */
	public static final boolean isHoverEvent(JsonTextEvent e) {
		checkNotNull(e);
		switch (e.action.toLowerCase()) {
		case "show_text":
		case "show_item":
		case "show_entity":
		case "show_achievement":
			return true;
		}
		return false;
	}
}
