package bot.modules.commands.octopi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;

import com.google.common.collect.ImmutableList;

import org.apache.commons.io.FileUtils;

import bot.modules.discord.Command;
import bot.modules.octopi.PrinterEnum;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.MessageCreateSpec;
import reactor.core.publisher.Mono;

public class CommandPrinterHistory extends Command
{

	private final String url;

	public CommandPrinterHistory(PrinterEnum printerEnum)
	{
		this.name = printerEnum.getName() + "-history";
		this.url = printerEnum + "/api/printer?history=true&apikey=" + printerEnum.getKey();
	}

    @Override
    public Mono<Message> handle(Message message, MessageChannel channel, ImmutableList<String> strings) {
		
		final File file;
		try {
			//Create temp file
			file = File.createTempFile("printer_history_" + System.currentTimeMillis(), ".json");

			try(InputStream stream = FileUtils.openInputStream(file)) {
			
	
				//Copy URL output to file
				FileUtils.copyURLToFile(new URL(url), file);
	
				//Post file to channel
				channel.createMessage(action -> new MessageCreateSpec().addFile(file.getName(), stream)).block();
	
				
				//Delete file when done
				Files.delete(file.toPath());
				
			} catch (Exception e) {
				channel.createMessage("Error processing command").block();//TODO ping admin
			} 

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
        return Mono.empty();
    }
}
