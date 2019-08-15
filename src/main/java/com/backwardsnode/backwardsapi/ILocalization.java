package com.backwardsnode.backwardsapi;

public interface ILocalization {

	public String command_Description(String command);
	
	public String feedback_ExclusiveSender(String locale, String targetSenderType);
	public String feedback_NoPermission(String locale);
	public String feedback_InCooldown(String locale);
}
