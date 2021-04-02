package bot.modules.octopi.models.api.job;

public class PrintJob {

	PrintJobFile file;
	PrintJobFilament filament;

	Integer estimatedPrintTime;
	Integer lastPrintTime;

	public PrintJob() {
		// TODO Auto-generated constructor stub
	}

	public PrintJobFile getFile() {
		return file;
	}

	public int getEstimatedPrintTime() {
		return estimatedPrintTime;
	}

	public int getLastPrintTime() {
		return lastPrintTime;
	}

	public PrintJobFilament getFilament() {
		return filament;
	}

}
