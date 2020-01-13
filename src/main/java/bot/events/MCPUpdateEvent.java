package bot.events;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.TimerTask;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.MalformedJsonException;

import bot.GeekBot;
import bot.commands.Minecraft;
import discord4j.core.object.entity.Channel;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.util.Snowflake;

public class MCPUpdateEvent extends TimerTask {
	static Logger log = LogManager.getLogger(MCPUpdateEvent.class);

	Channel channel = GeekBot.getClient().getGuildById(Snowflake.of("632708637122625538")).block()
			.getChannelById(Snowflake.of("637651124530446366")).block();
	MessageChannel mchan = (MessageChannel) channel;

	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	File BotPath = new File("C:\\GeekBot");

	File mcpVerionJson = new File("C:\\GeekBot\\MCPVersionManifest.json");
	File localMCPVersionJson = new File("C:\\Users\\Daley-Hawkins\\Downloads\\versions.json");

	@Override
	public void run() {
		String oldRelease = "";
		String oldSnapshot = "";
		String newRelease = "";
		String newSnapshot = "";

		try (BufferedReader data = Files.newBufferedReader(localMCPVersionJson.toPath())) {

			final JsonElement json = JsonParser.parseReader(data);

			if (!json.isJsonObject()) {
				throw new MalformedJsonException("Not a JsonObject");
			}
			final JsonObject root = json.getAsJsonObject();
			final JsonObject latest = root.get("latest").getAsJsonObject();

			oldRelease = latest.get(Minecraft.RELEASE).getAsString();
			oldSnapshot = latest.get(Minecraft.SNAPSHOT).getAsString();
			log.info("Old Release: {}", oldRelease);
			log.info("Old Snapshot: {}", oldSnapshot);

		} catch (Exception e) {
			log.catching(Level.ERROR, e);
		}
		try {
			URL minecraftVersionUrl = new URL("https://files.minecraftforge.net/maven/de/oceanlabs/mcp/versions.json");
			BotPath.mkdir();
			if (!mcpVerionJson.createNewFile()) {
				log.error("Couldn't make a Dang File");
			}
			FileUtils.copyURLToFile(minecraftVersionUrl, mcpVerionJson);

		} catch (MalformedURLException e1) {
			log.catching(e1);
		} catch (IOException e) {
			log.catching(e);
		}

		try (BufferedReader data = Files.newBufferedReader(localMCPVersionJson.toPath())) {

			final JsonElement json = JsonParser.parseReader(data);

			if (!json.isJsonObject()) {
				throw new MalformedJsonException("Not a JsonObject");
			}
			final JsonObject root = json.getAsJsonObject();
			final JsonObject latest = root.get("latest").getAsJsonObject();

			newRelease = latest.get(Minecraft.RELEASE).getAsString();
			newSnapshot = latest.get(Minecraft.SNAPSHOT).getAsString();

			log.info("New Release: {}", newRelease);
			log.info("New Snapshot: {}", newSnapshot);
		} catch (Exception e) {
			log.catching(Level.ERROR, e);
		}
		if (!oldRelease.isEmpty() || !oldSnapshot.isEmpty() || !newRelease.isEmpty() || !newSnapshot.isEmpty()) {
			if (!oldRelease.equals(newRelease)) {
				mchan.createMessage("New Stable Version of MCP Mappings is out! Version: " + newRelease);
			}
			if (!oldSnapshot.equals(newSnapshot)) {
				mchan.createMessage("New Snapshot Version of MCP Mappings is out! Version: " + newSnapshot);
			}
		}
	}

}
