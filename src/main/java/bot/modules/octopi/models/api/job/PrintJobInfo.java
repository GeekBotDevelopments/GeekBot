package bot.modules.octopi.models.api.job;

import lombok.Data;

@Data
public class PrintJobInfo {

	private PrintJob job;
	private PrintJobProgress progress;
	private String state;
}
