package com.umnirium.mc.frostbite;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.List;

public class ColdProtection {
    ConfigManager config = new ConfigManager();

    public boolean isColdProtected(Player player) {
        return isWearingLeather(player) || isHavingFireEnchantment(player) || isHavingFireResistanceEffect(player);
    }

    public boolean isWearingLeather(Player player) {
        if (config.isWearingLeatherProtectionEnabled()) {
            HashSet<Material> leather = new HashSet<Material>(List.of(Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS));

            int leatherCount = 0;

            ItemStack[] armor = player.getInventory().getArmorContents();

            for (ItemStack itemStack : armor) {
                if (itemStack != null && leather.contains(itemStack.getType())) {
                    leatherCount++;
                }
            }

            return leatherCount >= config.getLeatherArmorCount();
        }

        return false;
    }

    public boolean isHavingFireEnchantment(Player player) {
        if (config.isHavingFireProtectionEnabled()) {
            int armorCount = 0;

            ItemStack[] armor = player.getInventory().getArmorContents();

            for (ItemStack itemStack : armor) {
                if (itemStack != null && itemStack.containsEnchantment(Enchantment.FIRE_PROTECTION)) {
                    if (itemStack.getEnchantmentLevel(Enchantment.FIRE_PROTECTION) > config.getFireProtectionLevel()) {
                        armorCount++;
                    }
                }
            }

            return armorCount >= config.getFireProtectionCount();
        }

        return false;
    }

    public boolean isHavingFireResistanceEffect(Player player) {
        if (config.isHavingFireResistenceEnabled()) {
            return player.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE);
        }

        return false;
    }
}
