package bot.modules.configs;

import bot.GeekBot;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.util.StringUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
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

    private static String BotPrefix = "!gb";

    public static void load() throws IOException
    {
        final String ENV_PATH = System.getenv("main_config");
        final String DEFAULT_FILE = System.getProperty("user.dir") + "/" + DEFAULT_PATH + "/Config.properties";
        final File configFile = new File(StringUtil.isEmpty(ENV_PATH) ? DEFAULT_FILE : ENV_PATH);
        final File configFolder = configFile.getParentFile();

        GeekBot.MAIN_LOG.info(configFile);

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
            setGOOGLE_API_KEY(prop.getProperty("key.google"));
            setCHIRON_KEY(prop.getProperty("id.discord"));
            setDISCORD_SECRET(prop.getProperty("secret.discord"));
            setDISCORD_TOKEN(prop.getProperty("token.discord"));
            setYOUTUBE_ID(prop.getProperty("id.youtube"));
            setOWNER_ID(prop.getProperty("id.owner"));
            setLABRINTH_ID(prop.getProperty("id.labrinth"));
            setLABUPDATE(prop.getProperty("id.labupdate"));
            setCHIRON_KEY(prop.getProperty("key.chiron"));
            setCHIRON_URL(prop.getProperty("url.chiron"));
            setMINDUSTRY_URL(prop.getProperty("url.mindustry"));
            setDATABASE_URL(prop.getProperty("url.database"));
            setDATABASE_PASS(prop.getProperty("password.database"));
            setDATABASE_USER(prop.getProperty("username.database"));
            setENDER_KEY(prop.getProperty("key.ender"));
            setENDER_URL(prop.getProperty("url.ender"));

            LOGGER.info("Keys loaded");
        }
    }



    // -----GETTERS-&-SETTERS----- //

    public static String getID() {
        return YOUTUBE_ID;
    }

    public static String getDiscordToken() {
        return DISCORD_TOKEN;
    }

    public static String getDiscordId() {
        return DISCORD_ID;
    }

    public static String getDisordSecret() {
        return DISCORD_SECRET;
    }

    public static String getBotPrefix() {
        return BotPrefix;
    }

    public static Date get24() {
        Date date = new Date();
        date.setHours(24);
        date.setMinutes(0);
        return date;
    }

    /**
     * @return the gOOGLE_API_KEY
     */
    public static String getGOOGLE_API_KEY() {
        return GOOGLE_API_KEY;
    }

    /**
     * @param gOOGLE_API_KEY the gOOGLE_API_KEY to set
     */
    public static void setGOOGLE_API_KEY(String gOOGLE_API_KEY) {
        GOOGLE_API_KEY = gOOGLE_API_KEY;
    }

    /**
     * @return the dISCORD_TOKEN
     */
    public static String getDISCORD_TOKEN() {
        return DISCORD_TOKEN;
    }

    /**
     * @param dISCORD_TOKEN the dISCORD_TOKEN to set
     */
    public static void setDISCORD_TOKEN(String dISCORD_TOKEN) {
        DISCORD_TOKEN = dISCORD_TOKEN;
    }

    /**
     * @return the dISCORD_ID
     */
    public static String getDISCORD_ID() {
        return DISCORD_ID;
    }

    /**
     * @param dISCORD_ID the dISCORD_ID to set
     */
    public static void setDISCORD_ID(String dISCORD_ID) {
        DISCORD_ID = dISCORD_ID;
    }

    /**
     * @return the dISCORD_SECRET
     */
    public static String getDISCORD_SECRET() {
        return DISCORD_SECRET;
    }

    /**
     * @param dISCORD_SECRET the dISCORD_SECRET to set
     */
    public static void setDISCORD_SECRET(String dISCORD_SECRET) {
        DISCORD_SECRET = dISCORD_SECRET;
    }

    /**
     * @return the yOUTUBE_ID
     */
    public static String getYOUTUBE_ID() {
        return YOUTUBE_ID;
    }

    /**
     * @param yOUTUBE_ID the yOUTUBE_ID to set
     */
    public static void setYOUTUBE_ID(String yOUTUBE_ID) {
        YOUTUBE_ID = yOUTUBE_ID;
    }

    /**
     * @param oWNER_ID the oWNER_ID to set
     */
    public static void setOWNER_ID(String oWNER_ID) {
        OWNER_ID = oWNER_ID;
    }

    /**
     * @param cHIRON_KEY the cHIRON_KEY to set
     */
    public static void setCHIRON_KEY(String cHIRON_KEY) {
        CHIRON_KEY = cHIRON_KEY;
    }

    /**
     * @param cHIRON_URL the cHIRON_URL to set
     */
    public static void setCHIRON_URL(String cHIRON_URL) {
        CHIRON_URL = cHIRON_URL;
    }

    /**
     * @param mINDUSTRY_URL the mINDUSTRY_URL to set
     */
    public static void setMINDUSTRY_URL(String mINDUSTRY_URL) {
        MINDUSTRY_URL = mINDUSTRY_URL;
    }

    /**
     * @return the dATABASE_URL
     */
    public static String getDATABASE_URL() {
        return DATABASE_URL;
    }

    /**
     * @param dATABASE_URL the dATABASE_URL to set
     */
    public static void setDATABASE_URL(String dATABASE_URL) {
        DATABASE_URL = dATABASE_URL;
    }

    /**
     * @return the dATABASE_USER
     */
    public static String getDATABASE_USER() {
        return DATABASE_USER;
    }

    /**
     * @param dATABASE_USER the dATABASE_USER to set
     */
    public static void setDATABASE_USER(String dATABASE_USER) {
        DATABASE_USER = dATABASE_USER;
    }

    /**
     * @return the dATABASE_PASS
     */
    public static String getDATABASE_PASS() {
        return DATABASE_PASS;
    }

    /**
     * @param dATABASE_PASS the dATABASE_PASS to set
     */
    public static void setDATABASE_PASS(String dATABASE_PASS) {
        DATABASE_PASS = dATABASE_PASS;
    }

    /**
     * @return the eNDER_KEY
     */
    public static String getENDER_KEY() {
        return ENDER_KEY;
    }

    /**
     * @param eNDER_KEY the eNDER_KEY to set
     */
    public static void setENDER_KEY(String eNDER_KEY) {
        ENDER_KEY = eNDER_KEY;
    }

    /**
     * @return the eNDER_URL
     */
    public static String getENDER_URL() {
        return ENDER_URL;
    }

    /**
     * @param eNDER_URL the eNDER_URL to set
     */
    public static void setENDER_URL(String eNDER_URL) {
        ENDER_URL = eNDER_URL;
    }



    /**
     * @return the MINDUSTRY_URL
     */
    public static String getMINDUSTRY_URL() {
        return MINDUSTRY_URL;
    }

    public static String getCHIRON_KEY() {
        return CHIRON_KEY;
    }

    public static String getCHIRON_URL() {
        return CHIRON_URL;
    }

    public static String getLABRINTH_ID() {
        return LABRINTH_ID;
    }

    public static void setLABRINTH_ID(String lABRINTH_ID) {
        LABRINTH_ID = lABRINTH_ID;
    }

    public static String getLABUPDATE() {
        return LABUPDATE;
    }

    public static void setLABUPDATE(String lABUPDATE) {
        LABUPDATE = lABUPDATE;
    }


    public static String getOWNER_ID() {
        return OWNER_ID;
    }
}
