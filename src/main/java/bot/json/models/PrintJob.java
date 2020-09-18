package bot.json.models;

public class PrintJob {
	PrintFile file;
	Integer estimatedPrintTime;
	Integer lastPrintTime;
	PrintFilament filament;

	public PrintJob() {
		// TODO Auto-generated constructor stub
	}

	public PrintFile getFile() {
		return file;
	}

	public int getEstimatedPrintTime() {
		return estimatedPrintTime;
	}

	public int getLastPrintTime() {
		return lastPrintTime;
	}

	public PrintFilament getFilament() {
		return filament;
	}

}
