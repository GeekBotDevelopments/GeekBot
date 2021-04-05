package bot.modules.octopi.models.api.job;

import lombok.Data;

/**
 * https://docs.octoprint.org/en/master/api/datamodel.html#progress-information
 */
@Data
public class PrintJobProgress
{
	private Float completion;
	private Integer filepos;
	private Integer printTime;
	private Integer printTimeLeft;
	private String printTimeLeftOrigin;
}
