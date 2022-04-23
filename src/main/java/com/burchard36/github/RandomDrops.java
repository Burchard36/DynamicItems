package com.burchard36.github;

import com.burchard36.github.manager.ConfigManager;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

@Getter
public final class RandomDrops extends JavaPlugin {

    private ConfigManager configManager;
    private Random random;
    private Logger dropLogger;

    @Override
    public void onEnable() {
        this.random = new Random();
        this.configManager = new ConfigManager(this);
        this.dropLogger = new Logger(this);
    }

    @Override
    public void onDisable() {
    }

    public static String textOf(final String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
