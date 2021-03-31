package bot.modules.commands.octopi;

import bot.modules.discord.Command;
import bot.modules.octopi.PrinterEnum;
import bot.modules.octopi.PrinterUtilities;
import com.google.common.collect.ImmutableList;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import reactor.core.publisher.Mono;

public class CommandPrinterJob extends Command {

  private final PrinterEnum printer;

  public CommandPrinterJob(PrinterEnum printer) {
    super(printer.getName() + "-job");
    this.printer = printer;
  }

  @Override
  public Mono<Message> handle(Message message, MessageChannel channel, ImmutableList<String> args) {
    channel
      .getRestChannel()
      .createMessage(PrinterUtilities.createPrinterOutput(printer).asRequest())
      .block();
    return Mono.empty();
  }

}
