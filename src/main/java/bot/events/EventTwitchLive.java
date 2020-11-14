package bot.events;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import bot.json.models.TwitchStreamChange;

@Controller
public class EventTwitchLive {
    
    @MessageMapping("/twitch")
    public void TwitchLive(TwitchStreamChange data){

    }

}
