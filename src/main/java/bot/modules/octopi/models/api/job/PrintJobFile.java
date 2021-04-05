package bot.modules.octopi.models.api.job;

import lombok.Data;

/**
 * https://docs.octoprint.org/en/master/api/datamodel.html#file-information
 */
@Data
public class PrintJobFile
{
	private String date;
	private String display;
	private String name;
	private String origin;
	private String path;
	private Integer size;
}
