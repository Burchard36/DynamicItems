package com.burchard36.github.loader;

import com.burchard36.github.RandomDrops;
import com.burchard36.github.item.RandomItem;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

import static com.burchard36.github.RandomDrops.textOf;

public class ItemLoader {

    public final RandomDrops plugin;

    public ItemLoader(final RandomDrops pluginInstance) {
        this.plugin = pluginInstance;

        final File itemDir = new File(this.plugin.getDataFolder(), "/items");
        if (!itemDir.exists()) if (itemDir.mkdirs())
            Bukkit.getLogger().info(textOf("&aSuccessfully created /items directory as it didn't exist."));

        final File[] directory = itemDir.listFiles();
        if (directory == null) {
            Bukkit.getLogger().info("&cNo items detected in /items, why don't you make some ;)");
            return;
        }

        for (final File file : directory) {
            if (file.isDirectory()) continue;
            final FileConfiguration config = YamlConfiguration.loadConfiguration(file);

            for (final String nameKey : config.getKeys(false)) {
                final RandomItem item = RandomItem.loadFromConfig(config.getConfigurationSection(nameKey), this.plugin, nameKey);

                if (item == null) {
                    Bukkit.getLogger().warning("Config file: " + file.getName() + " could not be loaded! Please review your configuration(s)");
                    continue;
                }
            }
        }
    }
}
