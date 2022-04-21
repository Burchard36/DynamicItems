package com.burchard36.github.item.chances;

import com.burchard36.github.item.quality.ItemQualityField;
import com.burchard36.github.item.quality.Operation;
import com.burchard36.github.item.lore.LoreFormat;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nullable;

import java.util.List;

import static com.burchard36.github.RandomDrops.textOf;

public class ItemChance {

    @Getter @Setter
    private int chance;
    @Getter @Setter
    private ValueType valueType;
    @Getter @Setter
    private Object value;
    @Getter @Setter
    public ItemQualityField quality;
    @Getter @Setter
    private LoreFormat loreFormat;

    public ItemChance() {}

    public static ItemChance fromConfiguration(final @Nullable ConfigurationSection section) {
        if (section == null) return null;
        if (!section.isInt("Chance")) {
            return null;
        }
        final int configChance = section.getInt("Chance");

        ValueType type = null;
        if (section.isList("Value")) type = ValueType.STRING_ARRAY;
        if (section.isString("Value")) type = ValueType.STRING;
        if (section.isInt("Value")) type = ValueType.INTEGER;

        if (type == null) return null;

        ItemQualityField qualityField = null;
        if (section.isString("QualityChange")) {
            final String qualityString = section.getString("QualityChange");
            if (qualityString == null) return null;

            qualityField = ItemQualityField.fromString(qualityString);
        }

        if (section.get("Value") == null) return null;
        final Object obj = section.get("Value");



        return new ItemChanceBuilder()
                .setValue(obj)
                .setChance(configChance)
                .setLoreFormat(null)
                .setQuality(qualityField)
                .setValueType(type)
                .build();

    }

    public final String getValueAsString() throws InvalidValueGetException {
        if (this.valueType != ValueType.STRING) throw new InvalidValueGetException("Getting as String, when the configuration Chance Value was set as: " + this.valueType.name());
        return (String) this.value;
    }

    public final Integer getValueAsInteger() throws InvalidValueGetException {
        if (this.valueType != ValueType.INTEGER) throw new InvalidValueGetException("Getting as Integer, when configuration Chance Value was set as: " + this.valueType.name());
        return (Integer) this.value;
    }

    public final List<String> getValueAsStringList() throws InvalidValueGetException {
        if (this.valueType != ValueType.STRING_ARRAY) throw new InvalidValueGetException("Getting as StringList, when configuration Chance Value was set as: " + this.valueType.name());
        return (List<String>) this.value;
    }

    public static class ItemChanceBuilder {

        private int chance;
        private ValueType valueType;
        private Object value;
        public @Nullable ItemQualityField quality;
        private @Nullable LoreFormat format;

        ItemChanceBuilder() {

        }

        public ItemChance build() {
            final ItemChance chance = new ItemChance();
            chance.setChance(this.chance);
            chance.setQuality(this.quality);
            chance.setLoreFormat(this.format);
            chance.setValue(this.value);
            chance.setValueType(this.valueType);
            return chance;
        }

        public ItemChanceBuilder setChance(final int chance) {
            this.chance = chance;
            return this;
        }

        public ItemChanceBuilder setValueType(final ValueType valueType) {
            this.valueType = valueType;
            return this;
        }

        public ItemChanceBuilder setValue(final Object value) {
            this.value = value;
            return this;
        }

        public ItemChanceBuilder setQuality(final @Nullable ItemQualityField quality) {
            this.quality = quality;
            return this;
        }

        public ItemChanceBuilder setLoreFormat(final @Nullable LoreFormat loreFormat) {
            this.format = loreFormat;
            return this;
        }

    }

}
