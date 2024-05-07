package org.morj.bot.robomorj.database.engine.credentials;

import org.jetbrains.annotations.Nullable;
import org.morj.bot.robomorj.database.engine.url.DatabaseTypeUrl;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author TheDiVaZo
 * created on 08.05.2024
 */
@ConfigSerializable
public class DatabaseCredentials {
    @Setting(
            nodeFromParent = true
    )
    private Map<String, String> info;

    public DatabaseCredentials(String url, String username, String password) {
        this.info = new HashMap<>();
        this.info.put("url", url);
        if (username != null) {
            this.info.put("username", username);
        }

        if (password != null) {
            this.info.put("password", password);
        }

    }

    private DatabaseCredentials() {
        this("jdbc:mysql://localhost:3306/database", null, null);
    }

    public @Nullable String getPassword() {
        return this.info.get("password");
    }

    public @Nullable String getUsername() {
        return this.info.get("username");
    }

    public String getUrl() {
        return this.info.get("url");
    }

    public boolean havePassword() {
        return this.getPassword() != null;
    }

    public boolean haveUsername() {
        return this.getUsername() != null;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            DatabaseCredentials that = (DatabaseCredentials)o;
            return this.getUrl().equals(that.getUrl()) && Objects.equals(this.getUsername(), that.getUsername()) && Objects.equals(this.getPassword(), that.getPassword());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(this.getUrl(), this.getUsername(), this.getPassword());
    }

    public static DatabaseCredentials defaultCredentials() {
        return new DatabaseCredentials();
    }

    public static DatabaseCredentials getCredentials(DatabaseTypeUrl urlType, String url, String username, String password) {
        return new DatabaseCredentials(urlType.getUrl() + url, username, password);
    }
}

