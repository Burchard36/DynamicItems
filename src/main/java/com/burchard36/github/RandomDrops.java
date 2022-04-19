package com.burchard36.github;

import com.burchard36.github.manager.ConfigManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public final class RandomDrops extends JavaPlugin {

    @Getter
    private ConfigManager configManager;
    @Getter
    private Random random;
    @Getter
    private Logger dropLogger;

    @Override
    public void onEnable() {
        this.random = new Random();
        this.configManager = new ConfigManager(this);
        this.dropLogger = new Logger(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static String textOf(final String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static boolean isNull(final Object callingClazz,
                                 final String configPath,
                                 final String toTest) {
        if (toTest == null) {
            Bukkit.getLogger().info(textOf("&cConfig path for: &e" + callingClazz.getClass().getName() + " &cwas null for config path: &e" + configPath));
            return true;
        } else return false;
    }
}
