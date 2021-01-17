package bot.events;

import bot.GeekBot;
import bot.modules.configs.MainConfig;
import com.github.koraktor.steamcondenser.steam.servers.SourceServer;
import net.dv8tion.jda.api.entities.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TimerTask;

public class EventStarboudServerReset extends TimerTask {
	private static Logger log = LogManager.getLogger();

	public EventStarboudServerReset() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		this.starbound_reset();
	}

	public synchronized void starbound_reset() {

		log.info("restarting Server");
		Role starbound = GeekBot.getClient().getGuildById(MainConfig.getLABRINTH_ID()).getRoleById(755597397710733352l);
		Path serverPath = Paths.get("E:\\Games\\Steam\\steamapps\\common\\Starbound_Dedicated_Server\\win64\\"); //TODO config
		GeekBot.getClient().getTextChannelById(MainConfig.getLABUPDATE())
				.sendMessage(starbound.getAsMention() + " server restart").submit();
		try {
			log.info("Waiting 5 minutes");
			// this.wait(300000);
		} catch (Exception e) {
			log.catching(e);
		}
		Runtime rs = Runtime.getRuntime();
		// Process process;
		// ProcessBuilder pb = new ProcessBuilder("start " + serverPath.toString() +
		// "starbound_server.exe");
		try {
			log.info("killing task");
			// rs.exec("Taskkill /IM starbound_server.exe /F");

			log.info("Starting task");

			InetAddress serverip = InetAddress.getByName("legendarygeek.ddns.net");
			GeekBot.starboundServer = new SourceServer(serverip, 21025);
			GeekBot.starboundServer.initSocket();
			GeekBot.starboundServer.initialize();
			log.info("Query for players: {}", GeekBot.starboundServer.getPlayers());
			log.info("auth successsful: {}", GeekBot.starboundServer.rconAuth("toma"));
			log.info("rcon list command: {}", GeekBot.starboundServer.rconExec("/help"));

			// log.info(GeekBot.get("steam://rungameid/533830"));
			// process = pb.start();
			// process = rs.exec(serverPath.toString() + "starbound_server.exe" , null,
			// serverPath.toFile());
			// EventStarboundLog starlog = new EventStarboundLog(process.getErrorStream());
		} catch (Exception e) {
			log.catching(e);
		}
		log.info("Restarted server");
	}

}
