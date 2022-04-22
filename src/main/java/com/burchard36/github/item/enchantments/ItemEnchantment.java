package com.burchard36.github.item.enchantments;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ItemEnchantment {

    private EnchantmentType enchantmentType;
    private String enchantmentName;
    private int minLevel;
    private int maxLevel;
    private int staticLevel;

    public static List<ItemEnchantment> fromStringList(final List<String> list) {
        final List<ItemEnchantment> newList = new ArrayList<>();
        list.forEach((ench) -> {
            ItemEnchantment val = fromString(ench);
            if (val != null) newList.add(val);
        });
        return newList;
    }

    public static ItemEnchantment fromString(final String string) {

        String[] enchant = string.split(":");

        final ItemEnchantment itemEnchantment = new ItemEnchantment();
        if (enchant.length == 2) { // Is a vanilla enchant
            itemEnchantment.setEnchantmentType(EnchantmentType.VANILLA);
            itemEnchantment.setEnchantmentName(enchant[0]);

            int min = -1, max = -1, staticVal = -1;

            if (enchant[1].split("-").length == 2) { // has a valid range
                try {
                    min = Integer.parseInt(enchant[1].split(":")[0]);
                    max = Integer.parseInt(enchant[1].split(":")[1]);
                } catch (final NumberFormatException ignored) {
                    return null;
                }
            } else {
                try {
                    staticVal = Integer.parseInt(enchant[1]);
                } catch (final NumberFormatException ignored) {
                    return null;
                }
            }

            itemEnchantment.setMinLevel(min);
            itemEnchantment.setMaxLevel(max);
            itemEnchantment.setStaticLevel(staticVal);
            return itemEnchantment;
        } else return null; // Support for AdvancedEnchanments soon
    }

}
