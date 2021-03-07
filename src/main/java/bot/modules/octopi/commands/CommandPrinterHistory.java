package bot.modules.octopi.commands;

import bot.modules.octopi.PrinterEnum;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;

public class CommandPrinterHistory extends Command {

	private final String url;

	public CommandPrinterHistory(PrinterEnum printerEnum)
	{
		this.name = printerEnum.getName() + "-history";
		this.hidden = true;
		this.url = printerEnum + "/api/printer?history=true&apikey=" + printerEnum.getKey();
	}

	@Override
	protected void execute(CommandEvent event) {
		File file;

		try {

			//Create temp file
			file = File.createTempFile("printer_history_" + System.currentTimeMillis(), ".json");

			//Copy URL output to file
			FileUtils.copyURLToFile(new URL(url), file);

			//Post file to channel
			event.getChannel().sendFile(file).submit();

			//Delete file when done
			Files.delete(file.toPath());
		} catch (Exception e) {
			event.getChannel().sendMessage("Error processing command").submit(); //TODO ping admin
		}
	}

}
