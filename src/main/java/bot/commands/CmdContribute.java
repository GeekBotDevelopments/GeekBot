package bot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class CmdContribute extends Command {

	public CmdContribute() {
		name = "contribute";
		help = "links to the bots dev server, may be counted as server ad, so dont do in a non advert area";
	}

	@Override
	protected void execute(CommandEvent event) {
		event.getChannel().sendMessage("to contribute to the bot, or get help with commands, visit the bots dev server here"
				+ " https://discord.gg/ADrTFRZ or create an issue on our github instead here issue on our github instead here"
				+ " <https://github.com/LegendaryGeek/GeekBot/issues>").submit();

	}

}
