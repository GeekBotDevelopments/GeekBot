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

public class CmdChironJob extends Command {
	Gson gson = new Gson();
	private static Logger log = LogManager.getLogger(CmdChironJob.class);

	public CmdChironJob() {
		name = "chiron-job";
		hidden = true;
	}

	@Override
	protected void execute(CommandEvent event) {
		// JsonObject json = null;
		// PrintJobInfo info = null;
		// Integer estimatedPrintTime = null;
		// int hours = 0;
		// int minutes = 0;
		// int seconds = 0;
		// EmbedBuilder builder = new EmbedBuilder();

		// try {
		// json = JsonParser
		// .parseString(GeekBot.get(GeekBot.getCHIRON_URL() + "/job?apikey=" +
		// GeekBot.getCHIRON_KEY()))
		// .getAsJsonObject();
		// info = gson.fromJson(json, PrintJobInfo.class);
		// estimatedPrintTime = info.getJob().getEstimatedPrintTime();

		// } catch (Exception e) {
		// log.catching(e);
		// }

		// if (estimatedPrintTime != null)
		// hours = estimatedPrintTime / 3600;
		// if (estimatedPrintTime != null)
		// minutes = (estimatedPrintTime % 3600) / 60;
		// if (estimatedPrintTime != null)
		// seconds = estimatedPrintTime % 60;

		// if (info.getJob().getFile().getName() != null)
		// builder.addField("File name", info.getJob().getFile().getName(), true);

		// if (estimatedPrintTime != null)
		// builder.addField("Estimaed Print Time", String.format("%02d:%02d:%02d",
		// hours, minutes, seconds), true);

		// if (info.getJob().getFilament().getLength() != null)
		// builder.addField("Filament Length",
		// info.getJob().getFilament().getLength().toString(), true);

		// if ((Float)info.getJob().getFilament().getVolume() != null)
		// builder.addField("Filament Volume",
		// info.getJob().getFilament().getVolume().toString(), true);

		// if ((Float)info.getProgress().getCompletion() != null)
		// builder.addField("Percent Done",
		// info.getProgress().getCompletion().toString(), true);

		// builder.addField("Printer State", info.getState(), true);

		event.getChannel().sendMessage(PrinterUtilities.PrinterJob(Printer.CHIRION).build()).submit();
	}

}
