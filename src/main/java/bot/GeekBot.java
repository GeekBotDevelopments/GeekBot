package bot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.security.auth.login.LoginException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.MalformedJsonException;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;

import bot.commands.CmdHug;
import bot.commands.CmdInvite;
import bot.commands.CmdPing;
import bot.commands.CmdStarboundRole;
import bot.commands.CmdStopBot;
import bot.commands.CmdChironHistory;
import bot.commands.CmdChironJob;
import bot.commands.CmdContribute;
import bot.commands.CmdUserInfo;
import bot.commands.EventStarboudServerReset;
import bot.commands.Minecraft;
import bot.events.MCPUpdateEvent;
import bot.events.MinecraftUpdateEvent;
import bot.events.WelcomeEvent;
import bot.json.models.ServerSettings;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class GeekBot {
	private static boolean NeverEndingVariable = true;
	private static final String BASEURL = "https://www.googleapis.com/youtube/v3";
	private static String GOOGLE_API_KEY;
	private static String DISCORD_TOKEN;
	private static String DISCORD_ID;
	private static String DISCORD_SECRET;
	private static String YOUTUBE_ID;
	private static String OWNER_ID;
	private static String LABRINTH_ID;
	private static String LABUPDATE;
	private static String CHIRON_KEY;
	private static String  CHIRON_URL;
	
	private static HttpTransport transport = new HttpTransport() {

		@Override
		protected LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
	};
	private static URL url1;
	private static GsonFactory factory;
	private static SearchListResponse sr;
	// private static EventDispatcher dispatcher;
	public static JDABuilder builder;
	public static JDA DisClient;
	final static CommandClientBuilder commandBuilder = new CommandClientBuilder();
	public static YouTube YTClient;
	private static String result;
	public static String botname = "GeekBot";
	private static String BotPrefix = "!gb";
	//private static final Map<String, Command> commands = new HashMap<>();
	private static long id;
	public List<ServerSettings> settingsList = new ArrayList<>();
	private static Logger log = LogManager.getLogger(GeekBot.class);
	public static final File BotPath = new File("C:\\GeekBot\\ServerSettings");
	private static Timer timer = new Timer();
	private static Configuration config = new Configuration();
	private static StreamSpeechRecognizer recog;
	
	private static Set<GatewayIntent> intents = new HashSet<>();
	
	static {
		intents.add(GatewayIntent.DIRECT_MESSAGES);
		intents.add(GatewayIntent.DIRECT_MESSAGE_TYPING);
		intents.add(GatewayIntent.GUILD_MEMBERS);
		intents.add(GatewayIntent.GUILD_MESSAGES);
		intents.add(GatewayIntent.GUILD_MESSAGE_TYPING);
	}

	public static void main(String[] args) throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		config.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        config.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        config.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

		recog = new StreamSpeechRecognizer(config);
		
		try (InputStream input = GeekBot.class.getClassLoader().getResourceAsStream("Config.properties")) {

			Properties prop = new Properties();
			if (input == null) {
				log.error("unable to find Config.properties");
				return;
			}

			log.info("Loading keys");
			prop.load(input);
			GOOGLE_API_KEY = prop.getProperty("key.google");
			DISCORD_ID = prop.getProperty("id.discord");
			DISCORD_SECRET = prop.getProperty("secret.discord");
			DISCORD_TOKEN = prop.getProperty("token.discord");
			YOUTUBE_ID = prop.getProperty("id.youtube");
			OWNER_ID = prop.getProperty("id.owner");
			LABRINTH_ID = prop.getProperty("id.labrinth");
			LABUPDATE = prop.getProperty("id.labupdate");
			CHIRON_KEY = prop.getProperty("key.chiron");
			CHIRON_URL = prop.getProperty("url.chiron");

		}

		if (!BotPath.exists()) {
			BotPath.mkdirs();
		}

		factory = new GsonFactory();
		// DisClient = new DiscordClientBuilder(GeekBot.getDiscordToken()).build();
		builder = new JDABuilder(AccountType.BOT).setToken(DISCORD_TOKEN);
		// StatusUpdate status = Presence.online(Activity.listening("to Portal 2 OST"));
		// DisClient.updatePresence(status);
		TimerTask task = new EventStarboudServerReset();

		YTClient = new YouTube.Builder(GeekBot.transport, factory, new HttpRequestInitializer() {

			@Override
			public void initialize(HttpRequest request) throws IOException {

			}
		}).setApplicationName(botname).build();
		YouTube.Search.List request = YTClient.search().list("id,snippet");

		request.setChannelId(getID());
		request.buildHttpRequest();
		log.info("request json content: " + request.getJsonContent());

		log.info("Java Properties: " + System.getProperties());

		for (final File entry : BotPath.listFiles()) {
			try (BufferedReader data = Files.newBufferedReader(entry.toPath())) {

				final JsonElement json = JsonParser.parseReader(data);

				if (!json.isJsonObject()) {
					throw new MalformedJsonException("Not a JsonObject");
				}
				//final ServerSettings guildsettings = gson.fromJson(json, ServerSettings.class);
				//log.info("Guild settings loaded for {} from file Successfully", guildsettings.getName());

				//ew GeekBot().settingsList.add(guildsettings);
			} catch (Exception e) {
				log.catching(Level.ERROR, e);
			}
		}

//		result = get(getBaseurl() + "/search?" + "part=snippet" + "&order=date" + "&channelId=" + getID() + "&key="
//				+ getYTApiKey());

//		sr = gson.fromJson(result, SearchListResponse.class);

	 

		// set discord events
		builder.addEventListeners(new WelcomeEvent());
		commandBuilder.setPrefix("!gb ");
		commandBuilder.setOwnerId(OWNER_ID);
		commandBuilder.addCommand(new CmdPing());
		commandBuilder.addCommand(new CmdInvite());
		commandBuilder.addCommand(new CmdContribute());
		commandBuilder.addCommand(new CmdHug());
		commandBuilder.addCommand(new CmdUserInfo());
		
		// commands not used  by the public
		commandBuilder.addCommand(new CmdStopBot());
		commandBuilder.addCommand(new CmdStarboundRole());
		commandBuilder.addCommand(new CmdChironHistory());
		commandBuilder.addCommand(new CmdChironJob());

		commandBuilder.setHelpWord("help");

		timer.schedule(task, get24());
		final CommandClient commandListener = commandBuilder.build();
		builder.addEventListeners(commandListener);
		builder.enableIntents(intents);

		try {
			DisClient = builder.build();
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DisClient.setAutoReconnect(true);
		DisClient.getPresence().setActivity(Activity.watching("for !gb help"));
		// DisClient.login().log().block();
		log.info(result);
		log.info("End Of Program");
	}

	public static String getCHIRON_KEY() {
		return CHIRON_KEY;
	}

	public static String getCHIRON_URL() {
		return CHIRON_URL;
	}

	public static String getLABRINTH_ID() {
		return LABRINTH_ID;
	}

	public static void setLABRINTH_ID(String lABRINTH_ID) {
		LABRINTH_ID = lABRINTH_ID;
	}

	public static String getLABUPDATE() {
		return LABUPDATE;
	}

	public static void setLABUPDATE(String lABUPDATE) {
		LABUPDATE = lABUPDATE;
	}

	public static String get(String url) throws IOException {
		// URL declaration
		URL obj = new URL(url);

		// URL connection
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// Request Settings
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");

		// check response code for an okay
		int responseCode = con.getResponseCode();
		log.info("GET Response Code: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			// Read the Response from the site
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			// Generate a response to return
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			// Close Reader
			in.close();

			// print result
			return response.toString();
		}
		return null;
	}
	
	public static int post(String hostUrl, String send) throws IOException{
		URL obj = new URL(hostUrl);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		
		try(OutputStream os = con.getOutputStream()){
			os.write(send.getBytes());
		}
		return con.getResponseCode();
	}



	// -----GETTERS-&-SETTERS----- //

	/**
	 * 
	 * @return the YouTube Data API's Base URL as a string
	 */
	public static String getBaseurl() {
		return BASEURL;
	}

//	public static EventDispatcher getDispatcher() {
//		return dispatcher;
//	}

	public static URL getUrl1() {
		return url1;
	}

	public static String getID() {
		return YOUTUBE_ID;
	}

	public static void setUrl1(URL url1) {
		GeekBot.url1 = url1;
	}

	public static JDA getClient() {
		return DisClient;
	}

	public static String getYTApiKey() {
		return GOOGLE_API_KEY;
	}

	public static String getDiscordToken() {
		return DISCORD_TOKEN;
	}

	public static String getDiscordId() {
		return DISCORD_ID;
	}

	public static String getDisordSecret() {
		return DISCORD_SECRET;
	}

	public static String getBotPrefix() {
		return BotPrefix;
	}

	public static Date get24() {
		Date date = new Date();
		date.setHours(24);
		date.setMinutes(0);
		return date;
	}
}
