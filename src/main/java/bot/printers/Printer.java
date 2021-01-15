package bot.printers;

import bot.GeekBot;

public enum Printer {
    ENDER(GeekBot.getENDER_KEY(), GeekBot.getENDER_URL()), 
    CHIRION(GeekBot.getCHIRON_KEY(), GeekBot.getCHIRON_URL());

    private String Key;
    private String Url;

    Printer(String Key, String Url) {
        this.Key = Key;
        this.Url = Url;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return Key;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return Url;
    }

}
