package com.umnirium.mc.frostbite;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class ColdProtection {
    ConfigManager config = new ConfigManager();

    public boolean isColdProtected(Player player) {
        return isWearingLeather(player);
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

            return leatherCount >= config.getLeatherArmorLevel();
        }

        return false;
    }
}
