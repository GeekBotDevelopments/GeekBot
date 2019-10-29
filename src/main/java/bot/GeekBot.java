package bot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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

import bot.commands.Command;
import bot.commands.Minecraft;
import bot.commands.hug;
import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.event.EventDispatcher;
import discord4j.core.event.domain.guild.MemberJoinEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Member;
import discord4j.core.object.presence.Activity;
import discord4j.core.object.presence.Presence;
import discord4j.core.object.util.Snowflake;

public class GeekBot {
	private static boolean NeverEndingVariable = true;
	private static final String BASEURL = "https://www.googleapis.com/youtube/v3";
	private static String GOOGLE_API_KEY;
	private static String DISCORD_TOKEN;
	private static String DISCORD_ID;
	private static String DISCORD_SECRET;
	private static HttpTransport transport = new HttpTransport() {

		@Override
		protected LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
	};
	private static URL url1;
	private static GsonFactory factory;
	private static String ID;
	private static SearchListResponse sr;
	private static EventDispatcher dispatcher;
	public static DiscordClient DisClient;
	public static YouTube YTClient;
	private static String result;
	public static String botname = "GeekBot";
	private static String BotPrefix = "!gb";
	private static final Map<String, Command> commands = new HashMap<>();
	private static long id;
	private static Logger log = LogManager.getLogger(GeekBot.class);

	static {
		commands.put("ping", event -> event.getMessage().getChannel().block()
				.createMessage(event.getMember().get().getMention() + " Pong!").block());

		commands.put("transsafezone", event -> event.getMessage().getChannel().block().createMessage(
				"Come Join TransSafezone! A server that is free of trans hate, and accepting no matter who you are! invite: https://discord.gg/fD3cWyJ")
				.block());

		commands.put("invite-bot", event -> event.getMessage().getChannel().block().createMessage(
				"https://discordapp.com/api/oauth2/authorize?client_id=426722296816861184&permissions=8&scope=bot")
				.block());

		commands.put("help",
				event -> event.getMessage().getChannel().block().createMessage(
						"until this gets more developed, join the bot's test server: https://discord.gg/ADrTFRZ")
						.block());

		commands.put("hug", event -> event.getMessage().getChannel().block().createMessage(hug.hug(event)).block());

		commands.put("latest minecraft version",
				event -> event.getMessage().getChannel().block().createMessage(Minecraft.MinecraftVersion()).block());

		commands.put("latest forge mappings",
				event -> event.getMessage().getChannel().block().createMessage(Minecraft.ForgeStatus()).block());

	}

	public static void main(String[] args) throws IOException {
		Gson gson = new Gson();

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

		}

		factory = new GsonFactory();
		DisClient = new DiscordClientBuilder(GeekBot.getDiscordToken()).build();
//		DisClient.updatePresence(Presence.online(Activity.listening(" for !gb")).asStatusUpdate());
		DisClient.updatePresence(Presence.online(Activity.listening("to Portal 2 OST")));

		YTClient = new YouTube.Builder(GeekBot.transport, factory, new HttpRequestInitializer() {

			@Override
			public void initialize(HttpRequest request) throws IOException {

			}
		}).setApplicationName(botname).build();
		YouTube.Search.List request = YTClient.search().list("id,snippet");

		GeekBot.ID = "UC5qTgnQwtojeVvOKncoNfRA";

		request.setChannelId(getID());
		request.buildHttpRequest();
		log.info("request json content: " + request.getJsonContent());

		log.info("Java Properties: " + System.getProperties());

//		result = get(getBaseurl() + "/search?" + "part=snippet" + "&order=date" + "&channelId=" + getID() + "&key="
//				+ getYTApiKey());

//		sr = gson.fromJson(result, SearchListResponse.class);

		// set discord events
		DisClient.getEventDispatcher().on(MessageCreateEvent.class).subscribe(event -> parseMessage(event));
		DisClient.getEventDispatcher().on(MemberJoinEvent.class)
				.subscribe(event -> welcome(event.getGuildId(), event.getMember(), event));
//		DisClient.getEventDispatcher().on(MinecraftEvent.class).subscribe(event -> event);
		DisClient.login().log().block();
		log.info(result);

		log.info("End Of Program");
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

	// -----Utility-Methods----- //

	/**
	 * 
	 * @return if the loop should continue being loopy
	 */
	public static boolean isNeverendingvar() {
		return NeverEndingVariable;
	}

	/**
	 * stops the bit
	 *
	 */
	public static void stop() {
		DisClient.logout().block();
		return;
	}

	/**
	 * restarts the bit
	 *
	 */
	public static void restart() {
		DisClient.logout().block();
		DisClient.login().block();
		return;
	}

	// -----BOT-STUFF----- //

	public static void welcome(Snowflake guildId, Member member, MemberJoinEvent eventIn) {
		eventIn.getGuild().block().getSystemChannel().block()
				.createMessage("welcome " + member.getMention() + " to " + eventIn.getGuild().block().getName() + "!")
				.block();
		if (eventIn.getGuildId().asString().equals("605590142647795732")) {
			eventIn.getGuild().block().getSystemChannel().block()
					.createMessage(
							"be sure to read <#618920323949133836> and introduce yourself in <#618908853446115348>")
					.block();
		}

	}

	public static void parseMessage(MessageCreateEvent eventIn) {
		if (eventIn.getMessage().getContent().isPresent()) {
			String Message1 = eventIn.getMessage().getContent().get().toString();

			log.debug("message: [" + Message1 + "]");
			for (final Map.Entry<String, Command> entry : commands.entrySet()) {
				// We will be using !gb as our "prefix" to any command in the system.
				if (Message1.startsWith("!gb " + entry.getKey())) {
					entry.getValue().execute(eventIn);
					break;
				}
			}
			if (!eventIn.getGuildId().get().asString().equals("542561748327202836")) {
				eventIn.getMessage().getContent()
						.ifPresent(c -> log.info(getMemberName(eventIn) + ": " + c.trim().toString()));
			}
		}
	}

	public static String getMemberName(MessageCreateEvent eventIn) {
		if (eventIn.getMember().get().isBot()) {
			return eventIn.getMember().get().getDisplayName() + "[BOT]";
		}
		return eventIn.getMember().get().getDisplayName();
	}

	public static Member getMemberFromID(long id, MessageCreateEvent event) {
		Member member;
		Snowflake Sid = Snowflake.of(id);
//		member = event.getGuild().block().getMemberById(id).block();
		log.debug("Getting member with id of: " + Sid.asString());

		member = DisClient.getMemberById(event.getGuildId().get(), Sid).block();

		log.debug("Member is: " + member.getDisplayName());
		return member;
	}

	// -----GETTERS-&-SETTERS----- //

	/**
	 * 
	 * @return the YouTube Data API's Base URL as a string
	 */
	public static String getBaseurl() {
		return BASEURL;
	}

	public static EventDispatcher getDispatcher() {
		return dispatcher;
	}

	public static URL getUrl1() {
		return url1;
	}

	public static String getID() {
		return ID;
	}

	public static void setDispatcher(EventDispatcher dispatcher) {
		GeekBot.dispatcher = dispatcher;
	}

	public static void setUrl1(URL url1) {
		GeekBot.url1 = url1;
	}

	public static void setID(String iD) {
		ID = iD;
	}

	public static DiscordClient getClient() {
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

}
