package bot.modules.octopi.commands;

import bot.GeekBot;
import bot.modules.discord.Command;
import bot.modules.octopi.PrinterEnum;
import bot.modules.rest.RestUtil;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import org.apache.http.conn.ConnectTimeoutException;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Robin Seifert on 3/31/2021.
 */
public class CommandVersion extends Command
{
    private static final String SUB_COMMAND_SERVER = "server";
    private static final String SUB_COMMAND_API = "api";

    public CommandVersion()
    {
        super("version");
    }

    @Override
    public Mono<Message> handle(Message message, MessageChannel channel, ImmutableList<String> args)
    {
        if (!args.isEmpty() && args.size() <= 2)
        {
            final String subCommand = args.size() == 1 ? SUB_COMMAND_SERVER : args.get(0);
            final String printerString = args.size() == 1 ? args.get(0) : args.get(1);

            final Optional<PrinterEnum> printerOptional = Arrays.stream(PrinterEnum.values())
                    .filter(p -> p.getName().equalsIgnoreCase(printerString))
                    .findFirst();

            if (!printerOptional.isPresent())
            {
                return channel.createMessage("No printer found by name `" + printerString + "` while running command `" + message.getContent() + "`");
            }

            final PrinterEnum printer = printerOptional.get();

            try
            {

                final String response = RestUtil.getString(printer.getUrl() + "/api/version?apikey=" + printer.getKey());
                final JsonObject json = JsonParser.parseString(response).getAsJsonObject();

                //Match command
                switch (subCommand)
                {
                    case SUB_COMMAND_SERVER:
                        return channel.createMessage("Printer `" + printer.getName() + "` is running OctoPrint server version `" + json.get("server").getAsString() + "` ");
                    case SUB_COMMAND_API:
                        return channel.createMessage("Printer `" + printer.getName() + "` is running OctoPrint api version `" + json.get("api").getAsString() + "` ");
                    default:
                        return channel.createMessage("Unknown command `" + message.getContent() + "`");
                }
            }
            //TODO handle no connection errors as offline messages
            catch (ConnectTimeoutException e2) {
                return channel.createMessage("Connection timed out while connecting to printer `" + printer.getName() + "`");
            }
            catch (Exception e)
            {
                GeekBot.MAIN_LOG.error("Failed to get version for printer" + printer, e);
                return channel.createMessage("Unexpected error while running command `" + message.getContent() + "`");
            }
        }
        else if (args.isEmpty())
        {
            return channel.createEmbed(embedCreateSpec -> {
                Arrays.stream(PrinterEnum.values()).forEach(printer -> {
                    try
                    {
                        final String response = RestUtil.getString(printer.getUrl() + "/api/version?apikey=" + printer.getKey());
                        if (response != null)
                        {
                            final JsonObject json = JsonParser.parseString(response).getAsJsonObject();
                            embedCreateSpec.addField(printer.getName(), json.get("text").getAsString(), false);
                        }
                        else
                        {
                            embedCreateSpec.addField(printer.getName(), "unknown", false);
                        }
                    }
                    catch (Exception e)
                    {
                        GeekBot.MAIN_LOG.error("Failed to get version for printer" + printer, e);
                        embedCreateSpec.addField(printer.getName(), "error", false);
                    }
                });
            });
        }
        return channel.createMessage("Unknown command `" + message.getContent() + "`");
    }
}
