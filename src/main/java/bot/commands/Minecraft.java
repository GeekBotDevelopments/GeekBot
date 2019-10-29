package bot.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import bot.json.models.ForgeMapping;

public class Minecraft {
	static Logger log = LogManager.getLogger(Minecraft.class);
	public static List<ForgeMapping> mappingsList = new ArrayList<>();

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
		String snapshotName = "snapshot";
		String stableName = "stable";
		int snapshotVersion = 0;
		String latestMCVersion = "1.12.2";
		ForgeMapping mapping;

		try {
			statusCheck = get("https://files.minecraftforge.net/maven/de/oceanlabs/mcp/versions.json");
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("is statusCheck an JsonObject:    {}", statusCheck.isJsonObject());
		log.info("is statusCheck an JsonArray:     {}", statusCheck.isJsonArray());
		log.info("is statusCheck an JsonPrimitive: {}", statusCheck.isJsonPrimitive());
		log.info("is statusCheck an JsonNull:      {}", statusCheck.isJsonNull());
		log.info("---------------------------------------------------");
		mapping = gson.fromJson(statusCheck, ForgeMapping.class);
		log.info("statusCheck-Version: {}", mapping.getVersion());
		log.info("statusCheck-SnapShot: {}", mapping.getSnapshot().size());
		log.info("statusCheck-Stable: {}", mapping.getStable().size());
		log.info("---------------------------------------------------");
		log.info(statusCheck);
		log.info("---------------------------------------------------");

		JsonElement JObject = statusCheck.getAsJsonObject();

		log.info("is JObject an JsonObject:    {}", JObject.isJsonObject());
		log.info("is JObject an JsonArray:     {}", JObject.isJsonArray());
		log.info("is JObject an JsonPrimitive: {}", JObject.isJsonPrimitive());
		log.info("is JObject an JsonNull:      {}", JObject.isJsonNull());
		log.info("---------------------------------------------------");
		mapping = gson.fromJson(JObject, ForgeMapping.class);
		log.info("JObject-Version: {}", mapping.getVersion());
		log.info("JObject-SnapShot: {}", mapping.getSnapshot().size());
		log.info("JObject-Stable: {}", mapping.getStable().size());
		log.info("---------------------------------------------------");
		log.info(JObject);
		log.info("---------------------------------------------------");

		MappingObject object = gson.fromJson(statusCheck, MappingObject.class);

		for (Map.Entry<String, JsonElement> entry : object.map.entrySet()) {
			ForgeMapping maps = gson.fromJson(entry.getValue().toString(), ForgeMapping.class);
			maps.version = entry.getKey();

		}
//		String jobjString = statusCheck.getAsString();
//		log.info("JObject as string: " + jobjString);

//		JsonElement versions = JObject.get("latest").getAsJsonObject();

		String message = "the latest snapshot is: " + snapshotVersion + " for minecraft version: " + latestMCVersion;

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
		log.info("GET Response Code: " + responseCode);
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
		log.error("GET Failed");
		return null;
	}

	class MappingObject {
		JsonObject map;
	}
}
