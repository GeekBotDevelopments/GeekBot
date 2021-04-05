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
	/** Currently this is the 'text' string value from the state */
	private String state;
}
