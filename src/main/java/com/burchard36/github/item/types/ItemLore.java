package com.burchard36.github.item.types;

import com.burchard36.github.Logger;
import com.burchard36.github.RandomDrops;
import com.burchard36.github.item.chances.ItemChance;
import com.burchard36.github.item.quality.ItemQualityField;
import com.burchard36.github.item.lore.LoreFormat;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

public class ItemLore {

    private final RandomDrops plugin;
    @Getter
    private final Logger logger;
    @Getter
    private LoreFormat loreFormat;
    @Getter
    private List<String> loreList;
    @Getter
    private String defaultsTo;
    @Getter
    private ItemQualityField quality;
    @Getter
    private HashMap<String, ItemChance> loreChances;

    public ItemLore(final ConfigurationSection loreConfig,
                    final RandomDrops pluginInstance,
                    final String itemName) {
        this.plugin = pluginInstance;
        this.logger = this.plugin.getDropLogger();

        if (loreConfig.isList("QualityChange")) {
            this.logger.debug("Using Lore QualityChange field, this will override all QualityChange's set in Chances section, items name: " + itemName);
            this.quality = ItemQualityField.fromConfiguration(loreConfig.getConfigurationSection("QualityChange"), itemName, this.getLogger());
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
        }

        final ConfigurationSection loreChances = loreConfig.getConfigurationSection("Chances");
        if (loreChances == null) {
            this.logger.warnServer("Chances for item: &e" + itemName + "&r was null when reached, if you feel this is an error contact a developer!");
        }

        for (final String key : loreChances.getKeys(false)) {
            final ConfigurationSection sec = loreChances.getConfigurationSection(key);
            if (sec == null) {
                this.logger.warnServer("When loading Chances for item: &e" + itemName + "&r the config key &e" + key + "&r was null when received, skipping this lore chance...");
                continue;
            }
        }

    }

    private static class LoreValue {

        @Getter
        private final List<String> lore;
        @Getter
        private final ItemQualityField quality;

        public LoreValue(final List<String> lore,
                         final @Nullable ItemQualityField ItemQualityField) {
            this.lore = lore;
            this.quality = ItemQualityField;
        }

    }
}
