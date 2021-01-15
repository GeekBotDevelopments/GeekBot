package bot.models.forge;

public class ForgeMapping implements Comparable<ForgeMapping> {
	String MCVersion = "";
	Integer MCMajor = 0;
	Integer MCMinor = 0;
	Integer MCPatch = 0;
	int[] snapshot;
	int[] stable;

	public ForgeMapping() {

	}

	public String getMCVersion() {
		return MCVersion;
	}

	public int[] getSnapshot() {
		return snapshot;
	}

	public int[] getStable() {
		return stable;
	}

	public void setMCVersion(String MCVersion) {
		this.MCVersion = MCVersion;
	}

	public void setSnapshot(int[] snapshot) {
		this.snapshot = snapshot;
	}

	public void setStable(int[] stable) {
		this.stable = stable;
	}

	@Override
	public int compareTo(ForgeMapping b) {
		int first = MCMajor.compareTo(b.MCMajor);
		if (first != 0)
			return first;
		int second = MCMinor.compareTo(b.MCMinor);
		if (second != 0)
			return second;
		int third = MCPatch.compareTo(b.MCPatch);
		return third;
	}

	/**
	 * @return the MCMajor
	 */
	public Integer getMCMajor() {
		return MCMajor;
	}

	/**
	 * @param MCMajor the MCMajor to set
	 */
	public void setMCMajor(Integer MCMajor) {
		this.MCMajor = MCMajor;
	}

	/**
	 * @return the MCMinor
	 */
	public Integer getMCMinor() {
		return MCMinor;
	}

	/**
	 * @param MCMinor the MCMinor to set
	 */
	public void setMCMinor(Integer MCMinor) {
		this.MCMinor = MCMinor;
	}

	/**
	 * @return the MCPatch
	 */
	public Integer getMCPatch() {
		return MCPatch;
	}

	/**
	 * @param MCPatch the MCPatch to set
	 */
	public void setMCPatch(Integer MCMinor2) {
		this.MCPatch = MCMinor2;
	}

}
