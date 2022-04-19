package com.burchard36.github.manager.configs;

import com.burchard36.github.RandomDrops;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

import static com.burchard36.github.RandomDrops.textOf;

public class WordConfigs {

    private final RandomDrops plugin;

    @Getter
    public List<String> verbList;
    @Getter
    public List<String> adjectiveList;
    @Getter
    public List<String> nounList;
    @Getter
    public List<String> verberList;
    @Getter
    public List<String> nameFormats;

    public WordConfigs(final RandomDrops pluginInstance) {
        this.plugin = pluginInstance;

        final File file = new File(this.plugin.getDataFolder(), "/name_keywords.yml");
        final FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        if (!config.isList("Verbs")) {
            Bukkit.getLogger().warning(textOf("No list of Verbs was provided, this may cause error's later on!s"));
        }

        if (!config.isList("Nouns")) {
            Bukkit.getLogger().warning(textOf("No list of Nouns was provided, this may cause error's later on!"));
        }

        if (!config.isList("Adjectives")) {
            Bukkit.getLogger().warning("No list of Adjectives was provided, this may cause error's later on!");
        }

        if (!config.isList("Verbers")) {
            Bukkit.getLogger().warning(textOf("No list of Verbers was provided, this may cause error's later on!"));
        }

        if (!this.plugin.getConfig().isList("NameFormats")) {
            Bukkit.getLogger().warning(textOf("No list of NameFormats was provided, this may cause error's later on!"));
        }

        this.adjectiveList = config.getStringList("Adjectives");
        this.nounList = config.getStringList("Nouns");
        this.verberList = config.getStringList("Verbers");
        this.verbList = config.getStringList("Verbs");
        this.nameFormats = this.plugin.getConfig().getStringList("NameFormats");
    }

    /**
     * Formats a string with randomized nound/adjective/verber/verb with color coding
     * @param text String to format
     * @return Formatted string
     */
    public String format(String text) {
        final int nounsSize = this.getNounList().size() - 1;
        final int adjectiveSize = this.getAdjectiveList().size() - 1;
        final int verberSize = this.getVerberList().size();
        final int verbSize = this.getVerbList().size();

        text = text.replaceAll("%noun%", this.getNounList().get(this.plugin.getRandom().nextInt(nounsSize)));
        text = text.replaceAll("%ajective%", this.getAdjectiveList().get(this.plugin.getRandom().nextInt(adjectiveSize)));
        text = text.replaceAll("%verber%", this.getVerberList().get(this.plugin.getRandom().nextInt(verberSize)));
        return textOf(text.replaceAll("%verb%", this.getVerbList().get(this.plugin.getRandom().nextInt(verbSize))));
    }

    /**
     * Gets a random format from config.yml
     * @return Random formatted string from config.yml
     */
    public final String getRandomFormat() {
        final int formatSize = this.getNameFormats().size() - 1;
        return textOf(this.format(this.getNameFormats().get(this.plugin.getRandom().nextInt(formatSize))));
    }
}
