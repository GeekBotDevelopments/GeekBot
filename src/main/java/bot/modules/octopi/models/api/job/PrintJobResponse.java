package bot.modules.octopi.models.api.job;

import lombok.Data;

@Data
public class PrintJobResponse
{

	private PrintJobInformation job;
	private PrintJobProgress progress;
	private String state;
}
