package bot.events;

import bot.GeekBot;
import bot.modules.configs.MainConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class EventStarboundLog extends Thread{
	private static Logger log = LogManager.getLogger();
	final Lock lock = new ReentrantLock();
	final Condition error = lock.newCondition();

	public EventStarboundLog(InputStream errorStream) {
		this.setName("StarBound Error Thread");
		lock.lock();
		String input;
		InputStream errors = errorStream;
		BufferedReader in = new BufferedReader(new InputStreamReader(errors));
		try{
			while((input = in.readLine()) != null)
			error.await();

			log.error(input);
			GeekBot.getClient().openPrivateChannelById(MainConfig.getOWNER_ID()).complete().sendMessage(input);
		} catch (Exception e) {
		log.catching(e);
		}
	}

}
