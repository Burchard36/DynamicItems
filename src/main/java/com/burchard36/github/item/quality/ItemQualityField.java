package com.burchard36.github.item.quality;

import lombok.Getter;

import javax.annotation.Nullable;

public class ItemQualityField {

    @Getter
    private final int amount;
    @Getter
    private final Operation operation;

    public ItemQualityField(final int amount,
                            final Operation operation) {
        this.amount = amount;
        this.operation = operation;
    }

    public static ItemQualityField fromString(@Nullable String quality) {
        if (quality == null) return null;
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
