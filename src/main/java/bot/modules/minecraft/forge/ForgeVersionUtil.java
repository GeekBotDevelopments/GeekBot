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
public class ForgeVersionUtil
{
    private static final Pattern versionKeyPattern = Pattern.compile("([0-9]+)-\\w+");
    public static final String VERSION_URL = "https://files.minecraftforge.net/maven/net/minecraftforge/forge/promotions_slim.json";

    public static void fetchForgeVersions(Consumer<ForgeVersion> consumer)
    {
        try
        {
            //Get Json data
            final String response = RestUtil.getString(VERSION_URL);
            final JsonObject json = JsonParser.parseString(response).getAsJsonObject();

            //Gather promos
            final JsonObject promos = json.getAsJsonObject("promos");

            promos.entrySet().forEach(entry -> {
                //Filtering out bad data.... because LEX
                final Matcher matcher = versionKeyPattern.matcher(entry.getKey());
                if (matcher.find())
                {
                    consumer.accept(new ForgeVersion(entry.getKey(), entry.getValue().getAsString()));
                }
            });
        }
        catch (Exception e)
        {
            GeekBot.MAIN_LOG.error(e);
        }
    }
}
