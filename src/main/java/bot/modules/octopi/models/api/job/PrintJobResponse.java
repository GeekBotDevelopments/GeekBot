package bot.modules.octopi.models.api.job;

import lombok.Data;

/**
 *  https://docs.octoprint.org/en/master/api/job.html#data-model
 */
@Data
public class PrintJobResponse
{
	private PrintJobInformation job;
	private PrintJobProgress progress;
	/** “Operational”, “Printing”, “Pausing”, “Paused”, “Cancelling”, “Error” or “Offline”. */ //TODO convert to ENUM
	private String state;
}
