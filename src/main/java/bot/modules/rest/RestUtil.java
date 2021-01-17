package bot.modules.rest;

import org.springframework.scheduling.annotation.Async;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Dark(DarkGuardsman, Robert) on 1/17/2021.
 */
public final class RestUtil
{
    private RestUtil()
    {
    }

    @Async
    public static String get(String url) throws IOException
    {
        // URL declaration
        URL obj = new URL(url);

        // URL connection
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Request Settings
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        // check response code for an okay
        int responseCode = con.getResponseCode();
        // log.info("GET Response Code: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK)
        { // success
            // Read the Response from the site
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            // Generate a response to return
            while ((inputLine = in.readLine()) != null)
            {
                response.append(inputLine);
            }
            // Close Reader
            in.close();
            con.disconnect();

            // print result
            return response.toString();
        }
        return null;
    }
}
