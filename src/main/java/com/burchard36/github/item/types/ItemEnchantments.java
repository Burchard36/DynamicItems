package com.burchard36.github.item.types;

import com.burchard36.github.Logger;
import com.burchard36.github.RandomDrops;
import com.burchard36.github.item.enchantments.ItemEnchantment;
import com.burchard36.github.item.quality.ItemQualityField;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ItemEnchantments {

    private final RandomDrops plugin;
    private final Logger logger;
    private ItemQualityField quality;
    private List<ItemEnchantment> enchants;

    public ItemEnchantments(final RandomDrops pluginInstance,
                            final ConfigurationSection section,
                            final String itemName) {
        this.plugin = pluginInstance;
        this.logger = this.getPlugin().getDropLogger();

        if (section.isString("QualityChange")) {
            this.getLogger().debug("Using static QualityChange for item: &e" + itemName + "&r, this will override all QualityChange's for any Chance's set");
            this.quality = ItemQualityField.fromString(section.getString("QualityChange"));
            if (this.quality == null) this.getLogger().warnServer("When using static QualityChange for Enchantments on item: &e" + itemName + "&r the QualityChange configuration field was invalid! Please review your configuration(s)");
        }

        if (section.isList("Enchantments")) {
            this.getLogger().debug("Using static Enchantments for item: &e" + itemName + "&r, this will override all Chances set for Enchantments on this item!");
            final List<String> enchList = section.getStringList("Enchantments");
            if (enchList.isEmpty()) {
                this.getLogger().warnServer("When using static Enchantments for item: &e" + itemName + "&r The 'Enchantments' List was empty when received! Using Chances list instead...");
            } else {
                this.enchants = ItemEnchantment.fromStringList(enchList);
                if (this.enchants.isEmpty()) {
                    this.getLogger().warnServer("When using static Enchantments for item: &e" + itemName + "&r The 'Enchantments' List was empty while processing, this is likely a configuration error, please review your configurations");
                    this.enchants = null;
                } else {
                    this.getLogger().debug("Enchantments for item: &e" + itemName + "&r is using a Static Enchantments list, will not attempt to load Chances list...");
                    return;
                }
            }
        }

        final ConfigurationSection chances = section.getConfigurationSection("Chances");
        if (chances == null) {
            this.getLogger().warnServer("When loading Enchantment Chances for item: &e" + itemName + "&r");
            return;
        }

    }

}
