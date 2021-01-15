package bot.events;

import java.util.TimerTask;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import bot.GeekBot;
import bot.printers.Printer;
import bot.printers.PrinterUtilities;

@Component
@Scope("prototype")
public class EventEnderJobDone extends Thread {

    private String[] args;

    //private static Logger log = LogManager.getLogger();

    public EventEnderJobDone() {
        //this.run();
    }

    @Override
    @Async
    public void run() {
        Logger log = LogManager.getLogger();
        String previousState = "";
        String currentState = "";
        Boolean shouldRun = true;
        Gson gson = new Gson();
        JsonObject json = null;

        while (shouldRun) {

            try {
                json = JsonParser
                        .parseString(GeekBot.get(Printer.ENDER.getUrl() + "/job?apikey=" + Printer.ENDER.getKey()))
                        .getAsJsonObject();

            } catch (Exception e) {
                log.catching(e);
            }
            currentState = json.get("state").getAsString();
            if (currentState.equals("Opertional") && previousState.equals("Printing")) {
                log.info("Printer Done");
                GeekBot.getClient().getTextChannelById(763350428296413215l).sendMessage("Printer Finished")
                        .embed(PrinterUtilities.PrinterJob(Printer.ENDER).build()).submit();
            }

            try {
                previousState = currentState;
                // Thread.sleep(1000);
            } catch (Exception e) {
                log.catching(e);
            }
        }
    }
}
