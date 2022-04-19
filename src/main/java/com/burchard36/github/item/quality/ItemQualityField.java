package com.burchard36.github.item.quality;

import com.burchard36.github.Logger;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

public class ItemQualityField {

    @Getter
    private final int amount;
    @Getter
    private Operation operation;

    public ItemQualityField(final int amount,
                            final Operation operation) {
        this.amount = amount;
        this.operation = operation;
    }

    public static ItemQualityField fromString(String quality) {
        final Operation op;
        if (quality.startsWith("+")) {
            op = Operation.ADD;
        } else op = Operation.SUBTRACT;
        quality = quality.replace("+", "").replace("-", "");
        int qualityInteger;
        try {
            qualityInteger = Integer.parseInt(quality);
        } catch (final NumberFormatException ex) {
            return null;
        }
        return new ItemQualityField(qualityInteger, op);
    }
}
