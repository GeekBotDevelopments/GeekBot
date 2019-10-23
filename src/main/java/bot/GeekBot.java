package bot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import com.google.api.services.youtube.model.SearchResult;
import com.google.gson.Gson;

import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.event.EventDispatcher;

public class GeekBot {
	private static final boolean NEVERENDINGVAR = true;
	private static final String BASEURL = "https://www.googleapis.com/youtube/v3";
	private static String GOOGLE_API_KEY;
	private static String DISCORD_TOKEN;
	private static String DISCORD_ID;
	private static String DISCORD_SECRET;
	private static URL url1;
	private static String ID;
	private static SearchResult sr;
	private static EventDispatcher dispatcher;
	public static DiscordClient client;

	public static void main(String[] args) throws IOException {
		try (InputStream input = GeekBot.class.getClassLoader().getResourceAsStream("Config.properties")) {

			Properties prop = new Properties();
			if (input == null) {
				System.out.println("unable to find Config.properties");
				return;
			}
			prop.load(input);
			GOOGLE_API_KEY = prop.getProperty("key.google");
			DISCORD_ID = prop.getProperty("id.discord");
			DISCORD_SECRET = prop.getProperty("secret.discord");
			

		}
		client = new DiscordClientBuilder(args[0]).build();

		GeekBot.ID = "UC5qTgnQwtojeVvOKncoNfRA";

		System.out.println("Java Properties: " + System.getProperties());

		String result = get(getBaseurl() + "/search?" + "part=snippet" + "&order=date" + "&channelId=" + getID()
				+ "&key=" + getApiKey());

		Gson gson = new Gson();
		sr = gson.fromJson(result, SearchResult.class);

		System.out.println(result);
		System.out.println(sr.getSnippet());

		System.out.println("End Of Program");
	}

	private static String get(String url) throws IOException {
		// URL declaration
		URL obj = new URL(url);

		// URL connection
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// Request Settings
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");

		// check response code for an okay
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code: " + responseCode);
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

	/**
	 * 
	 * @return if the loop should continue being loopy
	 */
	public static boolean isNeverendingvar() {
		return NEVERENDINGVAR;
	}

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

	public static String getGoogleApiKey() {
		return GOOGLE_API_KEY;
	}

	public static DiscordClient getClient() {
		return client;
	}

	public static String getGOOGLE_API_KEY() {
		return GOOGLE_API_KEY;
	}

	public static String getDISCORD_TOKEN() {
		return DISCORD_TOKEN;
	}

	public static String getDISCORD_ID() {
		return DISCORD_ID;
	}

	public static String getDISCORD_SECRET() {
		return DISCORD_SECRET;
	}

}
