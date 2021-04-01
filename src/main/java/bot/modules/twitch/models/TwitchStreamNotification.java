package bot.modules.twitch.models;

public class TwitchStreamNotification {
    String id;
    String user_id;
    String user_name;
    String game_id;
    String[] community_ids;
    String type;
    String title;
    int viewer_count;
    String started_at;
    String language;
    String thumbnail_url;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the user_id
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * @param user_id the user_id to set
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    /**
     * @return the user_name
     */
    public String getUser_name() {
        return user_name;
    }

    /**
     * @param user_name the user_name to set
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    /**
     * @return the game_id
     */
    public String getGame_id() {
        return game_id;
    }

    /**
     * @param game_id the game_id to set
     */
    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    /**
     * @return the community_ids
     */
    public String[] getCommunity_ids() {
        return community_ids;
    }

    /**
     * @param community_ids the community_ids to set
     */
    public void setCommunity_ids(String[] community_ids) {
        this.community_ids = community_ids;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the viewer_count
     */
    public int getViewer_count() {
        return viewer_count;
    }

    /**
     * @param viewer_count the viewer_count to set
     */
    public void setViewer_count(int viewer_count) {
        this.viewer_count = viewer_count;
    }

    /**
     * @return the started_at
     */
    public String getStarted_at() {
        return started_at;
    }

    /**
     * @param started_at the started_at to set
     */
    public void setStarted_at(String started_at) {
        this.started_at = started_at;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return the thumbnail_url
     */
    public String getThumbnail_url() {
        return thumbnail_url;
    }

    /**
     * @param thumbnail_url the thumbnail_url to set
     */
    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }
}
