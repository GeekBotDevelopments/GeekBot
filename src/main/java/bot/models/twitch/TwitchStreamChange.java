package bot.models.twitch;

public class TwitchStreamChange {
    TwitchStreamNotification[] data;

    /**
     * @return the data
     */
    public TwitchStreamNotification[] getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(TwitchStreamNotification[] data) {
        this.data = data;
    }
}
