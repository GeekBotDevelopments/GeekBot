package bot.modules.rest;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by Robin Seifert on 1/17/2021.
 */
public final class RestUtil
{
    private RestUtil()
    {
    }

    public static GetRequest getRequest(String url)
    {
        return Unirest.get(url)
                .header("accept", "application/json")
                //TODO pull version from code
                //TODO get web URL for bot
                .header("User-Agent", "Mozilla/5.0 (compatible; GeekBot/1.0; +https://github.com/LegendaryGeek/GeekBot");
    }

    @Nullable
    public static String getString(String url) throws IOException
    {
        try
        {
            final HttpResponse<String> httpResponse = getRequest(url).asString();

            if (httpResponse.getStatus() == HttpURLConnection.HTTP_OK)
            {
                return httpResponse.getBody();
            }
            //TODO handle other cases
            return null;
        }
        catch (UnirestException e)
        {
            if (e.getCause() instanceof IOException)
            {
                throw (IOException) e.getCause();
            }
            throw new RuntimeException(e.getCause());
        }
    }
}
