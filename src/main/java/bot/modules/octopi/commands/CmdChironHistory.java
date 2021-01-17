package bot.modules.octopi.commands;

import bot.modules.configs.MainConfig;
import com.google.common.io.Files;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;

public class CmdChironHistory extends Command {
	private static Logger log = LogManager.getLogger(CmdChironHistory.class);

	public CmdChironHistory() {
		name = "chiron-history";
		hidden = true;
	}

	@Override
	protected void execute(CommandEvent event) {
		log.info("chiron activated");
		File file;
		String url = MainConfig.getCHIRON_URL() + "/printer?history=true&apikey=" + MainConfig.getCHIRON_KEY();
		try {
			 Files.createTempDir();
			file = File.createTempFile("chironHistory", ".json");
			FileUtils.copyURLToFile(new URL(url), file);
			event.getChannel().sendFile(file).submit();
			file.delete();
		} catch (Exception e) {
			log.catching(e);
			event.getChannel().sendMessage("got IO Exception: " + e.getStackTrace()).submit();
		}
	}

}
