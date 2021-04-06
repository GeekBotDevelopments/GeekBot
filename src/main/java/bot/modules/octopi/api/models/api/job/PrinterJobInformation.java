package bot.modules.octopi.api.models.api.job;

import lombok.Data;

/**
 * https://docs.octoprint.org/en/master/api/datamodel.html#file-information
 */
@Data
public class PrinterJobInformation
{
	private Float averagePrintTime;
	private Float estimatedPrintTime;
	private Float lastPrintTime;

	private PrinterJobFilament filament;
	private PrinterJobFile file;

	private String user;
}
