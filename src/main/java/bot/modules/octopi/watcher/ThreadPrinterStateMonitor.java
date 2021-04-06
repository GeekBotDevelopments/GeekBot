package bot.modules.octopi.watcher;

import bot.GeekBot;
import bot.modules.octopi.PrinterEnum;
import bot.modules.octopi.api.data.OctoPrinter;
import bot.modules.octopi.api.models.api.state.PrinterStateData;
import bot.modules.octopi.api.models.api.state.PrinterStateResponse;
import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.channel.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Thread to monitor status of Octopi-print servers to see if the printer has completed it's job
 *
 * @author Robin "Darkguardsman" Seifert
 */
@Component
@Scope("prototype")
public class ThreadPrinterStateMonitor extends Thread
{
    //TODO convert this thread into a listener that receives a message from the printers and just outputs to discord

    private static final Logger logger = LogManager.getLogger();

    private static final String CHANNEL_OUTPUT = " <@&807485697841299457> printer %s is completed it's job";    //TODO move to a config

    private static final long OUTPUT_CHANNEL_ID = 763350428296413215l; //TODO move to a config
    private static final long REFRESH_TIMER = 10000; //TODO move to a config

    /** Discord guild to output chat commands in */
    public static Guild outputServer; //TODO make this a map of printer -> server(s)

    public boolean running = true;

    public ThreadPrinterStateMonitor()
    {
        setDaemon(true);
    }

    @Override
    @Async
    public void run()
    {
        logger.info("Printer state monitor thread started");

        //Run until killed, will sleep between runs
        while (running)
        {
            //Loop all printers
            Stream.of(PrinterEnum.values()).map(PrinterEnum::getPrinter).forEach(this::checkPrinter);

            //Busy-wait
            try
            {
                sleep(REFRESH_TIMER);
            }
            catch (InterruptedException e)
            {
                logger.catching(e);
            }
        }

        logger.info("Printer state monitor thread stopped");
    }

    private void checkPrinter(final OctoPrinter printer)
    {


        //Store previous state
        printer.setPreviousState(printer.getStateResponse()
                .get().orElseGet(PrinterStateResponse::new)
                .getState()
        );

        //Update endpoint data
        printer.refreshEndpointData();

        final String newState = Optional.ofNullable(
                printer.getStateResponse().get().orElseGet(PrinterStateResponse::new)
                        .getState()).orElseGet(PrinterStateData::new) //TODO improve to not generate 2 waste objects
                .getText();
        final String lastState = printer.getPreviousState().getText();

        if (newState != null && lastState == null)
        {
            outputServer.getChannelById(Snowflake.of(OUTPUT_CHANNEL_ID))
                    .map(Channel::getRestChannel)
                    .map(restChannel -> restChannel.createMessage(String.format("OctoServer for printer `%s` is now online", printer.getName())))
                    .doOnError(GeekBot.MAIN_LOG::error)
                    .subscribe();
        }
    }
}
