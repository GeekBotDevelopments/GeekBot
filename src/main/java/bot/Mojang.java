package bot;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Mojang {

	public void MojangStatus() {
		Gson gson = new Gson();
		Object statusCheck;
		
		try {
			statusCheck = GeekBot.get("https://status.mojang.com/check");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		JsonObject arrayObject = gson.toJson(statusCheck);
//		JsonArray array = arrayObject.getAsJsonArray();
		
		
	}

}
