package com.umnirium.mc.frostbite;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.List;

public class ColdProtection {
    private final ConfigManager config;

    public ColdProtection(ConfigManager config) {
        this.config = config;
    }


    public boolean isColdProtected(Player player) {
        return isWearingLeather(player) || isHavingFireEnchantment(player) || isHavingFireResistanceEffect(player) || isNearHeatSource(player);
    }

    public boolean isWearingLeather(Player player) {
        if (config.isWearingLeatherProtectionEnabled()) {
            HashSet<Material> leather = new HashSet<>(List.of(Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS));

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

    public boolean isNearHeatSource(Player player) {
        if (config.isHeatSourceEnabled()) {
            Location location = player.getLocation();

            int cx = location.getBlockX();
            int cy = location.getBlockY();
            int cz = location.getBlockZ();

            int radius = config.getHeatSourceRadius();

            for (int x = -radius; x < radius; x++) {
                for (int y = -radius; y < radius; y++) {
                    for (int z = -radius; z < radius; z++) {
                        Block block = location.getWorld().getBlockAt(cx + x, cy + y, cz + z);

                        if (config.getHeatSources().contains(block.getType().getKey().toString())) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
}
