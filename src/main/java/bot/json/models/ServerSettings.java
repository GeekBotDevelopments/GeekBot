package bot.json.models;

public class ServerSettings {
	String name;
	long guildId;
	long announcementChannelId;

	boolean recieveMinecraftSanpshotUpdates;
	boolean recieveMinecraftReleaseUpdates;
	boolean recieveMCPMappingUpdates;

	public ServerSettings() {
	}

	public String getName() {
		return name;
	}

	public long getGuildId() {
		return guildId;
	}

	public long getAnnouncementChannelId() {
		return announcementChannelId;
	}

	public boolean isRecieveMinecraftSanpshotUpdates() {
		return recieveMinecraftSanpshotUpdates;
	}

	public boolean isRecieveMinecraftReleaseUpdates() {
		return recieveMinecraftReleaseUpdates;
	}

	public boolean isRecieveMCPMappingUpdates() {
		return recieveMCPMappingUpdates;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setGuildId(long guildId) {
		this.guildId = guildId;
	}

	public void setAnnouncementChannelId(long announcementChannelId) {
		this.announcementChannelId = announcementChannelId;
	}

	public void setRecieveMinecraftSanpshotUpdates(boolean recieveMinecraftSanpshotUpdates) {
		this.recieveMinecraftSanpshotUpdates = recieveMinecraftSanpshotUpdates;
	}

	public void setRecieveMinecraftReleaseUpdates(boolean recieveMinecraftReleaseUpdates) {
		this.recieveMinecraftReleaseUpdates = recieveMinecraftReleaseUpdates;
	}

	public void setRecieveMCPMappingUpdates(boolean recieveMCPMappingUpdates) {
		this.recieveMCPMappingUpdates = recieveMCPMappingUpdates;
	}

}
