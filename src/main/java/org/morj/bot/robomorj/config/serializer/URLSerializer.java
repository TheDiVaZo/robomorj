package org.morj.bot.robomorj.config.serializer;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

/**
 * @author TheDiVaZo
 * created on 01.05.2024
 */
public class URLSerializer implements TypeSerializer<URL> {

    private final Pattern urlPrefix = Pattern.compile("^(https?://|ftp://).*");
    @Override
    public URL deserialize(Type type, ConfigurationNode node) throws SerializationException {
        if (node.isNull() || node.getString() == null) return null;
        try {
            String url = node.getString();
            if (!urlPrefix.matcher(node.getString()).matches()) url = "http://" + url;
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new SerializationException(e);
        }
    }

    @Override
    public void serialize(Type type, @Nullable URL obj, ConfigurationNode node) throws SerializationException {
        if (obj == null) node.raw(null);
        else node.set(obj.toString());
    }
}
