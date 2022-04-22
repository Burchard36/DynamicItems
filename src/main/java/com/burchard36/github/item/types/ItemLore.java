package com.burchard36.github.item.types;

import com.burchard36.github.Logger;
import com.burchard36.github.RandomDrops;
import com.burchard36.github.item.chances.ItemChance;
import com.burchard36.github.item.quality.ItemQualityField;
import com.burchard36.github.item.lore.LoreFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.List;

@Getter
public class ItemLore {

    private final RandomDrops plugin;
    private final Logger logger;
    private LoreFormat loreFormat;
    private List<String> loreList;
    private String defaultsTo;
    private ItemQualityField quality;
    private HashMap<String, ItemChance> loreChances;

    public ItemLore(final ConfigurationSection loreConfig,
                    final RandomDrops pluginInstance,
                    final String itemName) {
        this.plugin = pluginInstance;
        this.logger = this.plugin.getDropLogger();

        if (loreConfig.isString("QualityChange")) {
            this.logger.debug("Using Lore QualityChange field, this will override all QualityChange's set in Chances section, items name: &e" + itemName);
            this.quality = ItemQualityField.fromString(loreConfig.getString("QualityChange"));
            if (this.quality == null) this.logger.warnServer("When specifying a static QualityChange in item: &e" + itemName + "&r the value was null when reached! This item will now use Quality's from Chance's section.");
        }

        if (loreConfig.isString("Lore")) {
            this.logger.debug("Using static Lore field, this will override all Lore's set in Chances section for item: &e" + itemName);
            this.loreList = loreConfig.getStringList("Lore");
        }

        if (loreConfig.isString("LoreFormat")) {
            this.logger.debug("Using static LoreFormat field, this will override all LoreFormat's set in Chances for item: &e" + itemName);
            this.loreFormat = LoreFormat.fromConfiguration(loreConfig.getConfigurationSection("Format"));

            if (this.loreFormat == null) this.logger.warnServer("Invalid static LoreFormat for item: &e" + itemName + "&r please review your configuration(s)!");

        }

        if (this.loreList != null) {
            this.logger.debug("Using static Lore List for item: &e" + itemName + "&r skipping loading Chances...");
            return;
        }

        final ConfigurationSection loreChances = loreConfig.getConfigurationSection("Chances");
        if (loreChances == null) {
            this.logger.warnServer("Chances for item: &e" + itemName + "&r was null when reached, if you feel this is an error contact a developer!");
            return;
        }

        if (!loreConfig.isString("DefaultsTo")) {
            this.logger.warnServer("When loading lore for item: &e" + itemName + "&r You did not set a DefaultsTo value when ");
        } else this.defaultsTo = loreConfig.getString("DefaultsTo");


        this.loreChances = new HashMap<>();
        for (final String key : loreChances.getKeys(false)) {
            final ConfigurationSection sec = loreChances.getConfigurationSection(key);
            if (sec == null) {
                this.logger.warnServer("When loading Chances for item: &e" + itemName + "&r the config key &e" + key + "&r was null when received, skipping this lore chance...");
                continue;
            }

            final ItemChance chance = ItemChance.fromConfiguration(loreChances.getConfigurationSection(key));
            if (chance == null) {
                this.logger.warnServer("Lore chance for item: &e" + itemName + "&r at key: &e" + key + "&r is invalid! Please review your configuration!");
            }

        }

    }

    @Getter
    @AllArgsConstructor
    private static class LoreValue {
        private final List<String> lore;
        private final ItemQualityField quality;
    }
}
