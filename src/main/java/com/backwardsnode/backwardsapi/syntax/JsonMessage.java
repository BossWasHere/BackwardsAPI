package com.backwardsnode.backwardsapi.syntax;

import java.util.ArrayList;
import java.util.List;

import com.backwardsnode.backwardsapi.util.ChatUtil;

/**
 * A class for constructing JSON message strings
 *
 */
public class JsonMessage {

	/**
	 * The main text within the message
	 */
	public String text;
	/**
	 * The color of the main text
	 */
	public String color;
	/**
	 * If the main text should be displayed in bold characters
	 */
	public boolean bold;
	/**
	 * If the main text should be displayed with a strikethrough
	 */
	public boolean strikethrough;
	/**
	 * If the main text should be displayed with an underline
	 */
	public boolean underlined;
	/**
	 * If the main text should be displayed as slightly slanted
	 */
	public boolean italic;
	/**
	 * If the main text should be displayed as obfuscated characters
	 */
	public boolean obfuscated;
	
	/**
	 * The event which should be executed when the text is clicked
	 */
	public JsonTextEvent clickEvent;
	/**
	 * The event which should be executed when the pointer is hovered over the text
	 */
	public JsonTextEvent hoverEvent;
	
	/**
	 * The string insertion which should be used with the main text
	 */
	public String insertion;
	
	/**
	 * Sets the main text within the message
	 * @param text The text to set
	 * @return This {@link JsonMessage} for chaining 
	 */
	public JsonMessage setText(String text) {
		this.text = text;
		return this;
	}
	
	/**
	 * Sets the color of the main text
	 * @param color The color to set
	 * @return This {@link JsonMessage} for chaining 
	 */
	public JsonMessage setColor(String color) {
		this.color = color;
		return this;
	}
	
	/**
	 * Sets if the main text should be displayed in bold characters
	 * @param bold If the text should be bold
	 * @return This {@link JsonMessage} for chaining 
	 */
	public JsonMessage setBold(boolean bold) {
		this.bold = bold;
		return this;
	}
	
	/**
	 * Sets if the main text should be displayed with a strikethrough
	 * @param strikethrough If the text should be strikethrough
	 * @return This {@link JsonMessage} for chaining 
	 */
	public JsonMessage setStrikethrough(boolean strikethrough) {
		this.strikethrough = strikethrough;
		return this;
	}
	
	/**
	 * Sets if the main text should be displayed with an underline
	 * @param underlined If the text should be underlined
	 * @return This {@link JsonMessage} for chaining 
	 */
	public JsonMessage setUnderlined(boolean underlined) {
		this.underlined = underlined;
		return this;
	}
	
	/**
	 * Sets if the main text should be displayed as slightly slanted
	 * @param italic If the text should be italic
	 * @return This {@link JsonMessage} for chaining 
	 */
	public JsonMessage setItalic(boolean italic) {
		this.italic = italic;
		return this;
	}
	
	/**
	 * Sets if the main text should be displayed as obfuscated characters
	 * @param obfuscated If the text should be obfuscated
	 * @return This {@link JsonMessage} for chaining 
	 */
	public JsonMessage setObfuscated(boolean obfuscated) {
		this.obfuscated = obfuscated;
		return this;
	}
	
	/**
	 * Sets the event which should be executed when the text is clicked
	 * @param clickEvent The event to set
	 * @return This {@link JsonMessage} for chaining 
	 */
	public JsonMessage setClickEvent(JsonTextEvent clickEvent) throws IllegalArgumentException {
		if (!clickEvent.isClickEvent()) {
			throw new IllegalArgumentException("Text Event is not of method Click Event");
		}
		this.clickEvent = clickEvent;
		return this;
	}
	
	/**
	 * Sets the event which should be executed when the pointer is hovered over the text
	 * @param hoverEvent The event to set
	 * @return This {@link JsonMessage} for chaining 
	 */
	public JsonMessage setHoverEvent(JsonTextEvent hoverEvent) throws IllegalArgumentException {
		if (!hoverEvent.isHoverEvent()) {
			throw new IllegalArgumentException("Text Event is not of method Hover Event");
		}
		this.hoverEvent = hoverEvent;
		return this;
	}
	
	/**
	 * Sets the string insertion which should be used with the main text
	 * @param insertion The insertion text to set
	 * @return This {@link JsonMessage} for chaining 
	 */
	public JsonMessage setInsertion(String insertion) {
		this.insertion = insertion;
		return this;
	}
	
	/**
	 * Transforms this JsonMessage into a usable JSON string. The equivalent of {@linkplain ChatUtil.buildRawMessage(message)}.
	 * @return The string value of the JsonMessage
	 */
	@Override
	public String toString() {
		return ChatUtil.buildRawMessage(this);
	}
	
	/**
	 * A class representing multiple {@link JsonMessage} objects in a sorted list
	 *
	 */
	public static class CompoundJsonMessage {
		
		public List<JsonMessage> messages;
		
		public CompoundJsonMessage() {
			messages = new ArrayList<JsonMessage>();
		}
		
		public void addMessage(JsonMessage message) {
			messages.add(message);
		}
		
	}
}
