package bot.json.models;

public class MinecraftVersion {

	String id;
	String type;
	String url;
	String time;
	String releaseTime;

	public MinecraftVersion() {

	}

	public String getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public String getUrl() {
		return url;
	}

	public String getTime() {
		return time;
	}

	public String getReleaseTime() {
		return releaseTime;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}

}
