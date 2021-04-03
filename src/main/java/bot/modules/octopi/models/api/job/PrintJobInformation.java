package bot.modules.octopi.models.api.job;

import lombok.Data;

/**
 * https://docs.octoprint.org/en/master/api/datamodel.html#file-information
 */
@Data
public class PrintJobInformation
{
	private Float averagePrintTime;
	private Float estimatedPrintTime;
	private Float lastPrintTime;

	private PrintJobFilament filament;
	private PrintJobFile file;

	private String user;
}
