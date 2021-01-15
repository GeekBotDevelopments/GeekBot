package bot.events;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import bot.json.models.TwitchStreamChange;

@Service
@Controller
public class EventTwitchLive {
    private String[] args;
    private static Logger log = LogManager.getLogger();

    public EventTwitchLive(String[] args) {
        this.args = args;
    }

    @MessageMapping("/twitch")
    public void TwitchLive(TwitchStreamChange data) {
        this.run();
    }

    @Async
    // @Override
    public void run() {
        // super.run();
        // SpringApplication.run(EventTwitchLive.class,
        // this.args).setParent(GeekBot.geekbotContext);
        // log.info("Started Twitch Notification Thread");
    }

}
