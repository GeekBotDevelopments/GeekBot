package bot.modules.octopi.commands;

public class CommandPrinterStatus //extends Command
{
//    public CommandPrinterStatus()
//    {
//        this.hidden = true;
//        this.name = "printerStatus";
//    }
//
//    @Override
//    protected void execute(CommandEvent event)
//    {
//        final EmbedBuilder build = new EmbedBuilder();
//        final StringBuilder builder = build.getDescriptionBuilder();
//
//        //Create title
//        builder.append("Printer Status: \n");
//
//        //Create body
//        for(PrinterEnum printerEnum : PrinterEnum.values()) {
//
//            final String status = OctopiModule.printerStateMonitor.printerState.get(printerEnum);
//
//            builder.append(" [");
//            builder.append(printerEnum.ordinal());
//            builder.append("] ");
//            builder.append(printerEnum.getName());
//            builder.append(": ");
//            builder.append(status);
//            builder.append("\n");
//        }
//
//        event.getChannel().sendMessage(build.build()).submit();
//    }
}
