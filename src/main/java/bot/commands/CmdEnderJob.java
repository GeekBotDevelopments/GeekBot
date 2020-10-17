package bot.commands;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import bot.GeekBot;
import bot.json.Json;
import bot.json.models.PrintJobInfo;
import bot.printers.Printer;
import bot.printers.PrinterUtilities;
import net.dv8tion.jda.api.EmbedBuilder;

public class CmdEnderJob extends Command {
    Gson gson = new Gson();
    private static Logger log = LogManager.getLogger(CmdEnderJob.class);

    public CmdEnderJob() {
		name = "ender-job";
		hidden = true;
	}

	@Override
	protected void execute(CommandEvent event) {
        event.getChannel().sendMessage(PrinterUtilities.PrinterJob(event, Printer.ENDER).build()).submit();

	}

}
