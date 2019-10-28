package bot.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class Minecraft {

	public static String MinecraftVersion() {
		Gson gson = new Gson();
		JsonElement statusCheck = null;

		try {
			statusCheck = get("https://launchermeta.mojang.com/mc/game/version_manifest.json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JsonObject JObject = statusCheck.getAsJsonObject();
		JsonObject versions = JObject.get("latest").getAsJsonObject();

		String message = "latest release is: " + versions.get("release") + "; " + "the latest snapshot is: "
				+ versions.get("snapshot");

		return message;
	}

	public static String ForgeStatus() {
		Gson gson = new Gson();
		JsonElement statusCheck = null;
		String snapshotName = "snapyshot";
		int snapshotVersion = 0;
		String latestMCVersion = "1.12.2";

		try {
			statusCheck = get("https://files.minecraftforge.net/maven/de/oceanlabs/mcp/versions.json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JsonElement JObject = statusCheck.getAsJsonObject();

		JsonReader reader = new JsonReader(new StringReader(JObject.getAsString().toString()));
		try {
			reader.beginObject();
			latestMCVersion = reader.nextName();
			reader.beginObject();
			snapshotName = reader.nextName();
			snapshotVersion = reader.nextInt();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JsonObject versions = JObject.get("latest").getAsJsonObject();

		String message = "the latest " + snapshotName + " is: " + snapshotVersion + " for minecraft version: "
				+ latestMCVersion;

		return message;
	}

	public static JsonElement get(String url) throws IOException {
		Gson gson = new Gson();

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

			JsonParser parsely = new JsonParser();
			JsonElement jobj = parsely.parse(in);

			// Generate a response to return
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			// Close Reader
			in.close();

			// print result
			return jobj;
		}
		return null;
	}

}
