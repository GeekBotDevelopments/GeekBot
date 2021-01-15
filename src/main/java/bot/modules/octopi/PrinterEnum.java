package bot.modules.octopi;

import bot.GeekBot;

//TODO replace with a config
public enum PrinterEnum
{
    ENDER("ender5", GeekBot.getENDER_KEY(), GeekBot.getENDER_URL()),
    CHIRION("chiron", GeekBot.getCHIRON_KEY(), GeekBot.getCHIRON_URL());

    private final String name;
    private final String apiKey;
    private final String accessUrl;

    PrinterEnum(String name, String apiKey, String url)
    {
        this.name = name;
        this.apiKey = apiKey;
        this.accessUrl = url;
    }

    public String getName() {
        return this.name;
    }

    public String getKey()
    {
        return apiKey;
    }

    public String getUrl()
    {
        return accessUrl;
    }
}
