package bot.modules.octopi.models.api.job;

public class PrintJobProgress
{
	Float completion;
	Integer filepos;
	Integer printTime;
	Integer printTimeLeft;
	String printTimeLeftOrigin;

	public Float getCompletion() {
		return completion;
	}

	public Integer getFilepos() {
		return filepos;
	}

	public Integer getPrintTime() {
		return printTime;
	}

	public Integer getPrintTimeLeft() {
		return printTimeLeft;
	}

	public String getPrintTimeLeftOrigin() {
		return printTimeLeftOrigin;
	}

	public PrintJobProgress() {
		// TODO Auto-generated constructor stub
	}

}
