package bot.modules.octopi;

import bot.modules.configs.MainConfig;
import bot.modules.rest.RestUtil;
import com.mashape.unirest.request.GetRequest;

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

    //<editor-fold desc="Getters">
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
    //</editor-fold>

    //<editor-fold desc="Url Helpers">
    public String createUrl(String subPath) {
        //url >> http://chiron.local
        return String.format("%s/%s", getUrl(), subPath);
    }

    public String createApiUrl(String subPath) {
        //url >> http://chiron.local
        return String.format("%s/api/%s", getUrl(), subPath);
    }

    public GetRequest createApiGetRequest(String subPath) {
        return (GetRequest) RestUtil.getRequest(createApiUrl(subPath))
                .queryString("apikey", getKey());
    }
    //</editor-fold>
}
