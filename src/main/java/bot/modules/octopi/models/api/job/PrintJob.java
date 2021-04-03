package bot.modules.octopi.models.api.job;

import lombok.Data;

@Data
public class PrintJob {

	private PrintJobFile file;
	private PrintJobFilament filament;

	private Integer estimatedPrintTime;
	private Integer lastPrintTime;
}
