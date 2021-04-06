package bot.modules.octopi.api.models.api.job;

import lombok.Data;

/**
 *  https://docs.octoprint.org/en/master/api/job.html#data-model
 */
@Data
public class PrinterJobResponse
{
	private PrinterJobInformation job;
	private PrinterJobProgress progress;
	/** Currently this is the 'text' string value from the state */
	private String state;
}
