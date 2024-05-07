package org.morj.bot.robomorj.database.engine.url;

/**
 * @author TheDiVaZo
 * created on 08.05.2024
 */
public enum DatabaseTypeUrl {
    MYSQL("jdbc:mysql://"),
    H2("jdbc:h2://");

    private final String url;

    private DatabaseTypeUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }
}
