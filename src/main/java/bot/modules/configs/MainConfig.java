package bot.modules.configs;

import bot.GeekBot;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Dark(DarkGuardsman, Robert) on 1/17/2021.
 */
public class MainConfig
{
    public static final String DEFAULT_PATH = "Config";
    private static final Logger LOGGER = LogManager.getLogger();

    //private static String BASEURL = "https://www.googleapis.com/youtube/v3";
    private static String GOOGLE_API_KEY;
    private static String DISCORD_TOKEN;
    private static String DISCORD_ID;
    private static String DISCORD_SECRET;
    private static String YOUTUBE_ID;
    private static String OWNER_ID;
    private static String LABRINTH_ID;
    private static String LABUPDATE;
    private static String CHIRON_KEY;
    private static String CHIRON_URL;
    private static String MINDUSTRY_URL;
    private static String DATABASE_URL;
    private static String DATABASE_USER;
    private static String DATABASE_PASS;
    private static String ENDER_KEY;
    private static String ENDER_URL;

    private static String BOT_PREFIX;

    public static void load() throws IOException
    {
        final String ENV_PATH = System.getenv("main_config");
        final String DEFAULT_FILE = System.getProperty("user.dir") + "/" + DEFAULT_PATH + "/Config.properties";
        final File configFile = new File(ENV_PATH == null || ENV_PATH.trim().isEmpty() ? DEFAULT_FILE : ENV_PATH);
        final File configFolder = configFile.getParentFile();

        //Output file location
        GeekBot.MAIN_LOG.info(String.format("Config File Location: %s", configFile.getAbsolutePath()));

        //Create folders if missing
        if (!configFolder.exists() && !configFolder.mkdirs()) {
            throw new RuntimeException("Failed to create config file");
        }
        else if(!configFile.exists()) {
            throw new FileNotFoundException(configFile.getAbsolutePath());
        }

        //Load configs
        try (InputStream input = FileUtils.openInputStream(configFile)) {
            final Properties prop = new Properties();

            //Load file
            LOGGER.info("Loading configs");
            prop.load(input);

            //Read values
            BOT_PREFIX = (prop.getProperty("bot.prefix"));
            GOOGLE_API_KEY = (prop.getProperty("key.google"));
            CHIRON_KEY = (prop.getProperty("id.discord"));
            DISCORD_SECRET = (prop.getProperty("secret.discord"));
            DISCORD_TOKEN = (prop.getProperty("token.discord"));
            YOUTUBE_ID = (prop.getProperty("id.youtube"));
            OWNER_ID = (prop.getProperty("id.owner"));
            LABRINTH_ID = (prop.getProperty("id.labrinth"));
            LABUPDATE = (prop.getProperty("id.labupdate"));
            CHIRON_KEY = (prop.getProperty("key.chiron"));
            CHIRON_URL = (prop.getProperty("url.chiron"));
            MINDUSTRY_URL = (prop.getProperty("url.mindustry"));
            DATABASE_URL = (prop.getProperty("url.database"));
            DATABASE_PASS = (prop.getProperty("password.database"));
            DATABASE_USER = (prop.getProperty("username.database"));
            ENDER_KEY = (prop.getProperty("key.ender"));
            ENDER_URL = (prop.getProperty("url.ender"));

            LOGGER.info("Keys loaded");
        }
    }

    /**
     * @return the gOOGLE_API_KEY
     */
    public static String getGOOGLE_API_KEY() {
        return GOOGLE_API_KEY;
    }

    /**
     * @return the dISCORD_TOKEN
     */
    public static String getDISCORD_TOKEN() {
        return DISCORD_TOKEN;
    }

    /**
     * @return the dISCORD_ID
     */
    public static String getDISCORD_ID() {
        return DISCORD_ID;
    }

    /**
     * @return the dISCORD_SECRET
     */
    public static String getDISCORD_SECRET() {
        return DISCORD_SECRET;
    }

    /**
     * @return the yOUTUBE_ID
     */
    public static String getYOUTUBE_ID() {
        return YOUTUBE_ID;
    }

    /**
     * @return the oWNER_ID
     */
    public static String getOWNER_ID() {
        return OWNER_ID;
    }

    /**
     * @return the lABRINTH_ID
     */
    public static String getLABRINTH_ID() {
        return LABRINTH_ID;
    }

    /**
     * @return the lABUPDATE
     */
    public static String getLABUPDATE() {
        return LABUPDATE;
    }

    /**
     * @return the cHIRON_KEY
     */
    public static String getCHIRON_KEY() {
        return CHIRON_KEY;
    }

    /**
     * @return the cHIRON_URL
     */
    public static String getCHIRON_URL() {
        return CHIRON_URL;
    }

    /**
     * @return the mINDUSTRY_URL
     */
    public static String getMINDUSTRY_URL() {
        return MINDUSTRY_URL;
    }

    /**
     * @return the dATABASE_URL
     */
    public static String getDATABASE_URL() {
        return DATABASE_URL;
    }

    /**
     * @return the dATABASE_USER
     */
    public static String getDATABASE_USER() {
        return DATABASE_USER;
    }

    /**
     * @return the dATABASE_PASS
     */
    public static String getDATABASE_PASS() {
        return DATABASE_PASS;
    }

    /**
     * @return the eNDER_KEY
     */
    public static String getENDER_KEY() {
        return ENDER_KEY;
    }

    /**
     * @return the eNDER_URL
     */
    public static String getENDER_URL() {
        return ENDER_URL;
    }

    /**
     * @return the bOT_PREFIX
     */
    public static String getBOT_PREFIX() {
        return BOT_PREFIX;
    }
}
