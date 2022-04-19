package com.burchard36.github.item;

import com.burchard36.github.Logger;
import com.burchard36.github.RandomDrops;
import com.burchard36.github.item.types.ItemLore;
import com.burchard36.github.item.types.ItemName;
import com.burchard36.github.manager.configs.WordConfigs;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;

public class RandomItem {

    @Getter @Setter
    private ItemName itemName = null;
    @Getter @Setter
    private ItemLore itemLore = null;

    public RandomItem() {

    }

    public static RandomItem loadFromConfig(final ConfigurationSection config,
                                      final RandomDrops pluginInstance,
                                            final String itemName) {
        final Logger log = pluginInstance.getDropLogger();
        final RandomItem item = new RandomItem();
        for (final String key : config.getKeys(false)) {

            final ConfigurationSection sec = config.getConfigurationSection(key);
            if (sec == null) {
                log.warnServer("Configuration section for item name: " + itemName + " was null, this warning can likely be ignored, if you run into any issues with this item, contact a developer.");
                continue;
            }

            switch(key) {

                case "Name" -> {
                    item.setItemName(new ItemName(sec, itemName, pluginInstance));
                    item.getItemName().format(pluginInstance.getConfigManager().getWordConfig());
                }

                case "Lore" -> {

                }

            }

        }

        if (item.getItemName() == null) {

            final WordConfigs wordConfig = pluginInstance.getConfigManager().getWordConfig();

            // no need to call ItemName#format, WordConfigs#getRandomFormat() does this already
            item.setItemName(new ItemName(wordConfig.getRandomFormat(), pluginInstance));
        }

        return item;
    }

}
