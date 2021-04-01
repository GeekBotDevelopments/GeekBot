package bot;

import bot.modules.commands.CommandsModule;
import bot.modules.configs.MainConfig;
import bot.modules.discord.DiscordModule;
import bot.modules.minecraft.forge.ForgeModule;
import bot.modules.octopi.OctopiModule;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@SpringBootApplication
@RestController
@EnableAsync
public class GeekBot extends SpringBootServletInitializer
{
    public static final Logger MAIN_LOG = LogManager.getLogger(GeekBot.class);
    public static final Gson GSON = new Gson();

    public static ConfigurableApplicationContext springApplicationContext;

    public static void main(String[] args) throws IOException
    {
        springApplicationContext = SpringApplication.run(GeekBot.class, args);

        MainConfig.load();
        CommandsModule.load();
        //StarboundModule.load();
        //VoiceModule.load();
        OctopiModule.load();
        ForgeModule.load();

        //Load last as this blocks the thread in a wait
        DiscordModule.load();
    }

    @GetMapping("/hello") //TODO move to it's own controller class
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name)
    {
        return String.format("Hello %s!", name);
    }
}
