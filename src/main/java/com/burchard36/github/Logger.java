package com.burchard36.github;

import lombok.Getter;

import static com.burchard36.github.RandomDrops.textOf;

public class Logger {

    @Getter
    private final RandomDrops plugin;
    private boolean debug;

    public Logger(final RandomDrops pluginInstance) {
        this.plugin = pluginInstance;
        this.debug = this.plugin.getConfig().getBoolean("DebugMode", false);
    }

    public void logMessage(final String msg) {
        this.plugin.getLogger().info(textOf(msg));
    }

    public void warnServer(final String msg) {
        this.plugin.getLogger().warning(textOf(msg));
    }

    public void debug(final String msg) {
        if (!this.debug) return;
        this.plugin.getLogger().info(textOf("&aDEBUG ::&r " + msg));
    }

}
