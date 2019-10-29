package bot.json.models;

import java.util.ArrayList;
import java.util.List;

public class ForgeMapping {
	public String version = "";
	List<Integer> snapshot = new ArrayList<>();
	List<Integer> stable = new ArrayList<>();

	public ForgeMapping() {

	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @return the stable
	 */
	public List<Integer> getStable() {
		return stable;
	}

	/**
	 * @return the snapshot
	 */
	public List<Integer> getSnapshot() {
		return snapshot;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @param stable the stable to set
	 */
	public void setStable(List<Integer> stable) {
		this.stable = stable;
	}

	/**
	 * @param snapshot the snapshot to set
	 */
	public void setSnapshot(List<Integer> snapshot) {
		this.snapshot = snapshot;
	}

}

class Mappings {

}