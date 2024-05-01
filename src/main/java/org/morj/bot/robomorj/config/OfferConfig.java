package org.morj.bot.robomorj.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.entities.emoji.UnicodeEmoji;
import org.morj.bot.robomorj.config.core.ConfigHolder;
import org.morj.bot.robomorj.config.serializer.ColorSerializer;
import org.morj.bot.robomorj.config.serializer.EmojiSerializer;
import org.morj.bot.robomorj.config.serializer.URLSerializer;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.serialize.TypeSerializerCollection;

import java.awt.*;
import java.io.File;
import java.net.URL;

/**
 * @author TheDiVaZo
 * created on 01.05.2024
 */
@ConfigSerializable
@FieldDefaults(level = AccessLevel.PUBLIC)
public class OfferConfig extends ConfigHolder<OfferConfig> {
    public OfferConfig(File baseFilePath) {
        super(baseFilePath, TypeSerializerCollection.builder()
                .register(URL.class, new URLSerializer())
                .register(Emoji.class, new EmojiSerializer())
                .register(Color.class, new ColorSerializer())
                .build());
    }

    public OfferConfig() {
        this(null);
    }

    long offerChannelID = 0;
    long offerReplierLoreID = 0;
    long deletedOffersChannelID = 0;

    //Добавлено
    MessageConfig added = new MessageConfig(Color.decode("#59ffac"), "Введено", Emoji.fromUnicode("✨"));
    //Одобрено
    MessageConfig approved = new MessageConfig(Color.decode("#07f71f"), "Одобрено", Emoji.fromUnicode("✅"));
    //Отклонено
    MessageConfig rejected = new MessageConfig(Color.RED, "Отклонено", Emoji.fromUnicode("❌"));
    //В ожидании
    MessageConfig pending = new MessageConfig(Color.WHITE, "В ожидании", Emoji.fromUnicode("✨"));

    @ConfigSerializable
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PUBLIC)
    public static class MessageConfig {
        Color color;
        String displayName;
        Emoji emoji;
    }
}
