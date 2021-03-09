package bot.modules.minecraft.forge;

import bot.GeekBot;
import bot.modules.rest.RestUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dark(DarkGuardsman, Robert) on 3/8/2021.
 */
public class ForgeVersions
{
    private static Pattern versionKeyPattern = Pattern.compile("([0-9]+)-\\w+");
    private static final String VERSION_URL = "https://files.minecraftforge.net/maven/net/minecraftforge/forge/promotions_slim.json";

    public static void forgeVersions(Consumer<String> consumer) {
        try
        {
            //Get Json data
            final String response = RestUtil.get(VERSION_URL);
            final JsonObject json = JsonParser.parseString(response).getAsJsonObject();

            //Gather promos
            final JsonObject promos = json.getAsJsonObject("promos");

            promos.entrySet().forEach(entry -> {

                final Matcher matcher = versionKeyPattern.matcher(entry.getKey());
                if(matcher.find())
                {
                    final ForgeVersion version = new ForgeVersion(entry.getKey(), entry.getValue().toString());
                    consumer.accept(version.toString());
                }
            });
        }
        catch (Exception e) {
            GeekBot.MAIN_LOG.error(e);
        }
    }
}
