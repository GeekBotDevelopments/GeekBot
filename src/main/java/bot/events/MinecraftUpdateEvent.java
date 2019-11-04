package bot.events;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.TimerTask;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import bot.GeekBot;
import bot.commands.Minecraft;
import discord4j.core.object.entity.Channel;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.util.Snowflake;

public class MinecraftUpdateEvent extends TimerTask {
	static Logger log = LogManager.getLogger(MinecraftUpdateEvent.class);

	Channel channel = GeekBot.getClient().getGuildById(Snowflake.of("632708637122625538")).block()
			.getChannelById(Snowflake.of("637651124530446366")).block();
	MessageChannel mchan = (MessageChannel) channel;

	Gson gson = new Gson();

	File minecraftVerionJson = new File("https://launchermeta.mojang.com/mc/game/version_manifest.json");
	File localMinecraftVersionJson = new File("C:\\Users\\Daley-Hawkins\\Downloads\\version_manifest.json");

	File mcpVersionJson = new File("https://files.minecraftforge.net/maven/de/oceanlabs/mcp/versions.json");
	File localMCPVersionJson = new File("C:\\Users\\Daley-Hawkins\\Downloads\\versions.json");

	@Override
	public void run() {
		String oldRelease = "";
		String oldSnapshot = "";
		String newRelease = "";
		String newSnapshot = "";
		try (BufferedReader data = Files.newBufferedReader(localMinecraftVersionJson.toPath())) {

			final JsonElement json = JsonParser.parseReader(data);

			if (!json.isJsonObject()) {
				throw new Exception();
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
			BufferedReader in = Files.newBufferedReader(minecraftVerionJson.toPath());
			String input;
			BufferedWriter out = Files.newBufferedWriter(localMinecraftVersionJson.toPath(), StandardOpenOption.WRITE);
			while ((input = in.readLine()) != null) {
				out.write(input);
			}
			in.close();
			out.close();
		} catch (Exception e) {
			log.catching(Level.ERROR, e);
		}

		try (BufferedReader data = Files.newBufferedReader(localMinecraftVersionJson.toPath())) {

			final JsonElement json = JsonParser.parseReader(data);

			if (!json.isJsonObject()) {
				throw new Exception();
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
				mchan.createMessage("New Release Version of Minecraft is out! Version: " + newRelease);
			}
			if (!oldSnapshot.equals(newSnapshot)) {
				mchan.createMessage("New Snapshot Version of Minecraft is out! Version: " + newSnapshot);
			}
		}
	}

}
