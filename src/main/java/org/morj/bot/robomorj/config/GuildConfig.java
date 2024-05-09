package org.morj.bot.robomorj.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import org.morj.bot.robomorj.config.core.ConfigHolder;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.io.File;

/**
 * @author TheDiVaZo
 * created on 08.05.2024
 */
@ConfigSerializable
@FieldDefaults(level = AccessLevel.PUBLIC)
public class GuildConfig extends ConfigHolder<GuildConfig> {
    public GuildConfig(File baseFilePath) {
        super(baseFilePath);
    }

    public GuildConfig() {
        this(null);
    }

    long guildId;

    @ConfigSerializable
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PUBLIC)
    public static class TicketConfig {
        long newTicketCategoryId;
        long pinTicketCategoryId;
        long closeTicketCategoryId;

        long createTicketPermissionRoleId;
        long pinTicketPermissionRoleId;
        long closeTicketPermissionRoleId;

        String ticketCreatorMessageTitle = "🧾 **Приветствуем всех в канале поддержки**";
        String ticketCreatorMessage = """
                Здесь вы можете оставлять жалобы и обращения к администрации
                Пожалуйста, нажмите на кнопку.
                """;
        String ticketCreatorButtonTitle = "✏";
        ButtonStyle ticketCreatorButtonStyle = ButtonStyle.SECONDARY;


    }
}
