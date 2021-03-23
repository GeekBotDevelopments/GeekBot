package bot.modules.commands;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bot.modules.discord.Command;
import bot.modules.discord.DiscordModule;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import reactor.core.publisher.Mono;

public class CmdStopBot extends Command
{
    private static Logger log = LogManager.getLogger();

    public CmdStopBot(String root) {
        super("stop");
    }

    @Override
    public boolean handle(Message message, MessageChannel channel, List<String> strings) {
		log.info("Shutting down");
		DiscordModule.client.logout();
		log.info("Shutdown");
		System.exit(0);
		log.info("Should not see thisss");
        return false;
    }
//	private static Logger log = LogManager.getLogger();
//
//	public CmdStopBot(){
//		name = "stop";
//		hidden = true;
//	}
//
//	@Override
//	protected void execute(CommandEvent event) {
//		log.info("Shutting down");
//		GeekBot.getClient().cancelRequests();
//		GeekBot.getClient().shutdown();
//		log.info("Shutdown");
//		System.exit(0);
//		log.info("Should not see thisss");
//	}

}
