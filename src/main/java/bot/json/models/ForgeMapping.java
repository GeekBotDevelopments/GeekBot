package bot.json.models;

public class ForgeMapping {
	public String MCVersion = "";
	int[] snapshot;;
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

	public void setMCVersion(String mCVersion) {
		MCVersion = mCVersion;
	}

	public void setSnapshot(int[] snapshot) {
		this.snapshot = snapshot;
	}

	public void setStable(int[] stable) {
		this.stable = stable;
	}

}