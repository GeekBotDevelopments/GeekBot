package bot.modules.twitch;

import bot.modules.twitch.models.TwitchStreamChange;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

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
        log.info("game id: {}, stream title: {}",data.getData()[0].getGame_id(), data.getData()[0].getTitle());
    }

}
