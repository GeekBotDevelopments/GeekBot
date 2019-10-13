package bot;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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

		try {
//			url1 = new URL(BASEURL + "/channel?part=snippet&channelId=" + getID() + "&key=" + getApiKey());
			url1 = new URL(getBaseurl()
					+ "/search?"
					+ "part=snippet"
					+ "&order=date"
					+ "&channelId=" + getID()
					+ "&key=" + getApiKey()
					);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpURLConnection con = null;
		try {
			con = (HttpURLConnection) url1.openConnection();
			con.setRequestMethod("GET");
			con.setDoOutput(true);

			System.out.println("Connection Request Properties: " + con.getRequestProperties());
			System.out.println("Connection Request Method: " + con.getRequestMethod());
			System.out.println("Connection Output Stream: " + con.getOutputStream().toString());
			System.out.println("Connection URL: " + con.getURL());
			System.out.println("Connection Connection Type: " + con.getContentType());
			System.out.println("Connection Response Code: " + con.getResponseCode());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		try {
//			transporter.createRequestFactory().buildGetRequest((GenericUrl) url1);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//		YouTube YT = new YouTube(null, null, null);

//		while (NEVERENDINGVAR) {
//			dispatcher.on(MessageCreateEvent.class).subscribe(event -> event.getMessage());
//		}

		System.out.println("End Of Program");
	}

	public static boolean isNeverendingvar() {
		return NEVERENDINGVAR;
	}

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
