package bot.json.models;

public class ForgeMapping {
	String version;
	int[] snaphsot;
	int[] stable;

	public ForgeMapping() {

	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @return the snaphsot
	 */
	public int[] getSnaphsot() {
		return snaphsot;
	}

	/**
	 * @return the stable
	 */
	public int[] getStable() {
		return stable;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @param snaphsot the snaphsot to set
	 */
	public void setSnaphsot(int[] snaphsot) {
		this.snaphsot = snaphsot;
	}

	/**
	 * @param stable the stable to set
	 */
	public void setStable(int[] stable) {
		this.stable = stable;
	}

}
