package bot.modules.octopi;

import bot.modules.configs.MainConfig;

//TODO replace with a config
public enum PrinterEnum
{
    ENDER("ender5", MainConfig.getENDER_KEY(), MainConfig.getENDER_URL()),
    CHIRION("chiron", MainConfig.getCHIRON_KEY(), MainConfig.getCHIRON_URL());

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
