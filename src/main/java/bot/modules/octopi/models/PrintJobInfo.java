package bot.modules.octopi.models;

import lombok.Data;

@Data
public class PrintJobInfo {

	private PrintJob job;
	private PrintJobProgress progress;
	private String state;
}
