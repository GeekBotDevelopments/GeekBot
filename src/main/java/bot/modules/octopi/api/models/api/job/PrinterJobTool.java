package bot.modules.octopi.api.models.api.job;

import lombok.Data;

/**
 * Documentation is outdated for Octoprint, each filment response returns a tool object
 */
@Data
public class PrinterJobTool
{
    private Float length;
	private Float volume;
}
