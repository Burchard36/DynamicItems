package com.burchard36.github.item.types;

import com.burchard36.github.Logger;
import com.burchard36.github.RandomDrops;
import com.burchard36.github.item.chances.InvalidValueGetException;
import com.burchard36.github.item.chances.ItemChance;
import com.burchard36.github.item.quality.ItemQualityField;
import com.burchard36.github.manager.configs.WordConfigs;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nullable;
import java.util.HashMap;

@Getter
public class ItemName {

    private final Logger logger;
    private final RandomDrops plugin;
    private String nameString = null;
    private String defaultsTo;
    private ItemQualityField qualityField = null;
    private HashMap<String, ItemChance> nameChances = null;



    public ItemName(final ConfigurationSection nameConfig,
                    final String itemName,
                    final RandomDrops plugin) {
        this.plugin = plugin;
        this.logger = this.plugin.getDropLogger();
        if (nameConfig.isString("QualityChange")) {
            this.getLogger().debug("Using static Name QualityChange field, overriding all qualities for item: &e" + itemName);
            String qualityString = nameConfig.getString("QualityChange");
            if (qualityString == null) {
                this.logger.warnServer("When loading static Name QualityChange field, the string received was null! Please review your configuration for the item: &e" + itemName + "&r. The plugin will now attempt to use QualityChange's from Chances");
            } else this.qualityField = ItemQualityField.fromString(qualityString);
        }

        if (nameConfig.isString("Format")) {
            this.getLogger().debug("Using Format field, overriding all Formats for item: &e" + itemName);
            this.nameString = nameConfig.getString("Name");
        }


        if (this.nameString != null) {
            this.getLogger().debug("Format was set for item: &e" + itemName + "&r preventing Chances from being loaded...");
            return; // prevent loading Chances field, if any set
        }

        final ConfigurationSection nameChances = nameConfig.getConfigurationSection("Chances");
        if (nameChances == null) {
            this.getLogger().warnServer("When loading item: " + itemName + " 'Chances' field was null, and you did not set a Format field! Are you okay? Review your configuration! Loading for this item will cancel, and this may lead to errors down the line!");
            return;
        }

        if (nameChances.isString("DefaultsTo")) {
            this.defaultsTo = nameChances.getString("DefaultsTo");
        } else this.getLogger().warnServer("You set a Chances for the item: " + itemName + " but there was not DefaultsTo value! This may cause an NPE later on down the line! I strongly recommend setting a DefaultsTo value when using Chances!");

        this.nameChances = new HashMap<>();
        for (final String key : nameChances.getKeys(false)) {
            final ItemChance chance = ItemChance.fromConfiguration(nameChances.getConfigurationSection(key));

            if (chance == null) {
                this.getLogger().warnServer("Invalid Item Name Chance detected for item: " + itemName + ". The invalid chances config key is: " + key);
                continue;
            }

            this.nameChances.putIfAbsent(key, chance);
        }
    }

    public ItemName(final String string,
                    final RandomDrops pluginInstance) {
        this.nameString = string;
        this.plugin = pluginInstance;
        this.logger = this.plugin.getDropLogger();
    }

    public void format(final WordConfigs config) {
        this.nameString = config.format(this.getNameString());
    }

    public NameValue getRandomName() throws InvalidValueGetException {
        if (this.nameString != null) {
            return new NameValue(this.nameString, this.qualityField);
        }

        for (final ItemChance chance : this.nameChances.values()) {
            final int intChance = this.plugin.getRandom().nextInt(100);
            if (chance.getChance() <= intChance) {

                String value = chance.getValueAsString();
                value = this.plugin.getConfigManager().getWordConfig().format(value);
                return new NameValue(value, chance.getQuality());
            }
        }

        final ItemChance chance = this.nameChances.get(this.getDefaultsTo());
        return new NameValue(chance.getValueAsString(), chance.getQuality());
    }

    private static class NameValue {

        @Getter
        private final String formattedName;
        @Getter
        private final ItemQualityField quality;

        public NameValue(String name, @Nullable ItemQualityField quality) {
            this.formattedName = name;
            this.quality = quality;
        }
    }

}
