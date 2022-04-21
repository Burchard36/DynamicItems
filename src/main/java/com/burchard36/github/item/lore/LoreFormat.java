package com.burchard36.github.item.lore;

import com.burchard36.github.Logger;
import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nullable;

public class LoreFormat {

    private final LoreType type;
    private final String configKey;

    public LoreFormat(final LoreType type,
                      final @Nullable String loreKey) {
        if (type == LoreType.OTHER && loreKey == null) {
            throw new InvalidLoreFormatConfigurationException("Attempting to use a static LoreFormat, but a key was not set to grab from config.yml!");
        }

        this.type = type;
        this.configKey = loreKey;
    }

    /**
     * Loads a LoreType from configuration
     * @param sec ConfigurationSection to load from
     * @return null if configuration was invalid
     */
    public static LoreFormat fromConfiguration(final ConfigurationSection sec) {
        if (sec == null) return null;
        final String stringType = sec.getString("Type");
        final String keyValue = sec.getString("Key");

        if (stringType == null) return null;

        LoreType type;
        try {
            type = LoreType.valueOf(stringType.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return null;
        }

        if (type == LoreType.OTHER && keyValue == null) return null;

        return new LoreFormat(type, keyValue);
    }

}
