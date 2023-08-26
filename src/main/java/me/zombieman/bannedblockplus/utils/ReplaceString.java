package me.zombieman.bannedblockplus.utils;

import me.zombieman.bannedblockplus.data.SaveBlockData;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ReplaceString {
    public static String replace(String string, Block block, Player p) {
        string = string.replace("%block%", block.getType().name())
                .replace("%block-name%", SaveBlockData.blockName(new ItemStack(block.getType())))
                .replace("%player%", p.getName());
        return string;
    }
}
