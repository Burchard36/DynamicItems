package com.burchard36.github.manager;

import com.burchard36.github.RandomDrops;
import com.burchard36.github.manager.configs.WordConfigs;
import lombok.Getter;

public class ConfigManager {

    @Getter
    private final WordConfigs wordConfig;

    public ConfigManager(final RandomDrops pluginInstance) {

        pluginInstance.saveDefaultConfig();
        pluginInstance.saveResource("name_keywords.yml", false);
        pluginInstance.saveResource("/items/SWORD.yml", false);
        pluginInstance.saveResource("/mobs/ZOMBIE.yml", false);

        this.wordConfig = new WordConfigs(pluginInstance);
    }

}
