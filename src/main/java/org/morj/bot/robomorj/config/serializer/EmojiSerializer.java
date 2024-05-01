package org.morj.bot.robomorj.config.serializer;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.entities.emoji.UnicodeEmoji;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

/**
 * @author TheDiVaZo
 * created on 01.05.2024
 */
public class EmojiSerializer implements TypeSerializer<Emoji> {
    @Override
    public Emoji deserialize(Type type, ConfigurationNode node) throws SerializationException {
        if (node.isNull() || node.getString() == null) return null;
        try {
            return Emoji.fromUnicode(node.getString());
        } catch (Exception e) {
            throw new SerializationException(e);
        }
    }

    @Override
    public void serialize(Type type, @Nullable Emoji obj, ConfigurationNode node) throws SerializationException {
        if (obj == null) node.raw(null);
        else if (!(obj instanceof UnicodeEmoji unicodeEmoji)) throw new SerializationException("Emoji must be unicode");
        else node.set(unicodeEmoji.getAsCodepoints());
    }
}
