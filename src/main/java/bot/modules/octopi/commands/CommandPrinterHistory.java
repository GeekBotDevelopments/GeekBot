package bot.modules.octopi.commands;

public class CommandPrinterHistory //extends Command
{

//	private final String url;
//
//	public CommandPrinterHistory(PrinterEnum printerEnum)
//	{
//		this.name = printerEnum.getName() + "-history";
//		this.hidden = true;
//		this.url = printerEnum + "/api/printer?history=true&apikey=" + printerEnum.getKey();
//	}
//
//	@Override
//	protected void execute(CommandEvent event) {
//		File file;
//
//		try {
//
//			//Create temp file
//			file = File.createTempFile("printer_history_" + System.currentTimeMillis(), ".json");
//
//			//Copy URL output to file
//			FileUtils.copyURLToFile(new URL(url), file);
//
//			//Post file to channel
//			event.getChannel().sendFile(file).submit();
//
//			//Delete file when done
//			Files.delete(file.toPath());
//		} catch (Exception e) {
//			event.getChannel().sendMessage("Error processing command").submit(); //TODO ping admin
//		}
//	}

}
