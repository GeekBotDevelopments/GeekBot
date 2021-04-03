package bot.modules.octopi.models.api.job;

import lombok.Data;

/**
 * Documentation is outdated for Octoprint, each filment response returns a tool object
 */
@Data
public class PrintJobTool
{
    private Float length;
	private Float volume;
}
