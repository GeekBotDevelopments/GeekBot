package bot;

import bot.commands.CmdContribute;
import bot.commands.CmdExitVoice;
import bot.commands.CmdHug;
import bot.commands.CmdInvite;
import bot.commands.CmdJoinVoice;
import bot.commands.CmdPing;
import bot.commands.CmdStopBot;
import bot.commands.CmdUserInfo;
import bot.events.WelcomeEvent;
import bot.modules.configs.MainConfig;
import bot.modules.octopi.PrinterEnum;
import bot.modules.octopi.commands.CmdPrinterHistory;
import bot.modules.octopi.commands.CommandPrinterJob;
import bot.modules.octopi.commands.CommandPrinterStatus;
import bot.modules.octopi.events.ThreadPrinterStateMonitor;
import bot.modules.starbound.CmdStarboundRole;
import com.github.koraktor.steamcondenser.steam.servers.SourceServer;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
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

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@RestController
@EnableAsync
public class GeekBot extends SpringBootServletInitializer
{

    public static final Logger MAIN_LOG = LogManager.getLogger(GeekBot.class);
    private static final Configuration config = new Configuration();

    public static JDA discordClient;
    public static SourceServer starboundServer;
    public static ConfigurableApplicationContext springApplicationContext;
    private static StreamSpeechRecognizer speechRecognizer;

    public static ThreadPrinterStateMonitor printerStateMonitor;

    public static void main(String[] args) throws IOException
    {
        springApplicationContext = SpringApplication.run(GeekBot.class, args);

        config.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        config.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        config.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

        speechRecognizer = new StreamSpeechRecognizer(config);

        MainConfig.load();

        final Set<GatewayIntent> intents = new HashSet<>();
        intents.add(GatewayIntent.DIRECT_MESSAGES);
        intents.add(GatewayIntent.DIRECT_MESSAGE_TYPING);
        intents.add(GatewayIntent.GUILD_MEMBERS);
        intents.add(GatewayIntent.GUILD_MESSAGES);
        intents.add(GatewayIntent.GUILD_MESSAGE_TYPING);
        intents.add(GatewayIntent.GUILD_VOICE_STATES);
        intents.add(GatewayIntent.GUILD_EMOJIS);
        intents.add(GatewayIntent.GUILD_MESSAGE_REACTIONS);

        final JDABuilder builder = JDABuilder.createDefault(MainConfig.getDiscordToken(), intents);
        final CommandClientBuilder commandBuilder = new CommandClientBuilder();

        registerEvents(builder);
        registerCommands(commandBuilder);

        //TODO document
        final CommandClient commandListener = commandBuilder.build();
        builder.setChunkingFilter(ChunkingFilter.ALL);
        builder.addEventListeners(commandListener);
        builder.enableIntents(intents);

        try
        {
            discordClient = builder.build();
            discordClient.setAutoReconnect(true);
            discordClient.getPresence().setActivity(Activity.watching("for !gb help"));
        }
        catch (Exception e)
        {
            MAIN_LOG.catching(e);
        }

        //Kick off thread
        printerStateMonitor = new ThreadPrinterStateMonitor();
        printerStateMonitor.start();
    }

    private static void registerEvents(@Nonnull JDABuilder builder)
    {
        builder.addEventListeners(new WelcomeEvent());
    }

    private static void registerCommands(@Nonnull CommandClientBuilder commandBuilder)
    {
        //Commands Settings
        commandBuilder.setPrefix("!gb ");
        commandBuilder.setHelpWord("help");
        commandBuilder.setOwnerId(MainConfig.getOWNER_ID());

        //Main commands
        commandBuilder.addCommand(new CmdPing());
        commandBuilder.addCommand(new CmdInvite());
        commandBuilder.addCommand(new CmdContribute());
        commandBuilder.addCommand(new CmdHug());
        commandBuilder.addCommand(new CmdUserInfo());
        commandBuilder.addCommand(new CmdStopBot());

        //Starbound commands
        commandBuilder.addCommand(new CmdStarboundRole());
        // commandBuilder.addCommand(new CmdStarBoundRestart());

        //Voice commands
        commandBuilder.addCommand(new CmdJoinVoice());
        commandBuilder.addCommand(new CmdExitVoice());

        //Octoprint commands
        commandBuilder.addCommand(new CmdPrinterHistory(PrinterEnum.CHIRION));
        commandBuilder.addCommand(new CmdPrinterHistory(PrinterEnum.ENDER));
        commandBuilder.addCommand(new CommandPrinterJob(PrinterEnum.CHIRION));
        commandBuilder.addCommand(new CommandPrinterJob(PrinterEnum.ENDER));
        commandBuilder.addCommand(new CommandPrinterStatus());
    }

    public static Configuration getConfig()
    {
        return config;
    }

    public static StreamSpeechRecognizer getSpeechRecognizer()
    {
        return speechRecognizer;
    }

    public static JDA getClient()
    {
        return discordClient;
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name)
    {
        return String.format("Hello %s!", name);
    }
}
