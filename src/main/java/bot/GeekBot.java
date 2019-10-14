package bot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.api.client.http.HttpTransport;

import discord4j.core.event.EventDispatcher;

public class GeekBot {
	private static final boolean NEVERENDINGVAR = true;
	private static final String BASEURL = "https://www.googleapis.com/youtube/v3";
	private static final String API_KEY = "AIzaSyBtrqtJDl64_H-BJeG7jY2RTUggENPTqWk";
	private static EventDispatcher dispatcher;
	private static HttpTransport transporter;
	private static URL url1;
	private static String ID;

	public static void main(String[] args) throws IOException {

		ID = "UC5qTgnQwtojeVvOKncoNfRA";
		
		System.out.println("Java Properties: " + System.getProperties());
		
		String result = get(getBaseurl() + "/search?" + "part=snippet" + "&order=date" + "&channelId=" + getID()
				+ "&key=" + getApiKey());

		System.out.println(result);

//		while (NEVERENDINGVAR) {
//			dispatcher.on(MessageCreateEvent.class).subscribe(event -> event.getMessage());
//		}

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

	public static HttpTransport getTransporter() {
		return transporter;
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

	public static void setTransporter(HttpTransport transporter) {
		GeekBot.transporter = transporter;
	}

	public static void setUrl1(URL url1) {
		GeekBot.url1 = url1;
	}

	public static void setID(String iD) {
		ID = iD;
	}

	public static String getApiKey() {
		return API_KEY;
	}

}
