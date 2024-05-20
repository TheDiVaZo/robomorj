package org.morj.bot.robomorj.core.config.serializer;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.entities.emoji.UnicodeEmoji;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.awt.*;
import java.lang.reflect.Type;

/**
 * @author TheDiVaZo
 * created on 01.05.2024
 */
public class ColorSerializer implements TypeSerializer<Color> {
    @Override
    public Color deserialize(Type type, ConfigurationNode node) throws SerializationException {
        if (node.isNull() || node.getString() == null) return null;
        try {
            return Color.decode(node.getString());
        } catch (Exception e) {
            throw new SerializationException(e);
        }
    }

    @Override
    public void serialize(Type type, @Nullable Color obj, ConfigurationNode node) throws SerializationException {
        if (obj == null) node.raw(null);
        else node.set(String.format("#%02x%02x%02x", obj.getRed(), obj.getGreen(), obj.getBlue()));
    }
}
