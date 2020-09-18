package bot.commands;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TimerTask;

import bot.GeekBot;
import net.dv8tion.jda.api.entities.Role;

public class EventStarboudServerReset extends TimerTask {

	public EventStarboudServerReset() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		Role starbound = GeekBot.getClient().getGuildById(GeekBot.getLABRINTH_ID()).getRoleById(755597397710733352l);
		Path serverPath = Paths.get("E:\\Games\\Steam\\steamapps\\common\\Starbound Dedicated Server\\win64");
		GeekBot.getClient().getTextChannelById(GeekBot.getLABUPDATE()).sendMessage(starbound.getAsMention() + " server restart").submit();
		try {
			this.wait(300000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		Runtime rs = Runtime.getRuntime();
		try {
			rs.exec("Taskkill /IM starbound_server.exe /F");
			rs.exec("start \"Starbound Server\" " + serverPath.toString() + " starbound_server.exe");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
