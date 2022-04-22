package com.burchard36.github.item.quality;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nullable;

@Getter @AllArgsConstructor
public class ItemQualityField {

    private final int amount;
    private final Operation operation;

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
