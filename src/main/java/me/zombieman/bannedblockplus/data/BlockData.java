package me.zombieman.bannedblockplus.data;

import me.zombieman.bannedblockplus.BannedBlockPlus;
import me.zombieman.bannedblockplus.managers.PlayerDataManager;
import me.zombieman.bannedblockplus.utils.ColorUtils;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class BlockData {
    private BannedBlockPlus plugin;
    public BlockData(BannedBlockPlus plugin) {
        this.plugin = plugin;
    }
    public void block(Block block, Player p, boolean isCanceled) {
        List<String> blocks = plugin.getConfig().getStringList("bannedBlocks");
        if (blocks.contains(block.getType().toString())) {
            if (!p.hasPermission("bannedblockplus.command.bypass")) {
                UUID pUUID = p.getUniqueId();
                boolean wantsToBypass = PlayerDataManager.getPlayerDataConfig(plugin, pUUID).getBoolean(pUUID + ".enabled", false);
                if (wantsToBypass) {
                    if (!isCanceled) {
                        block.setType(block.getType());
                        p.sendMessage(ColorUtils.color(plugin.getConfig().getString("bannedBlockMessage")
                                .replace("%block%", block.getType().name())
                                .replace("%block-name%", SaveBlockData.blockName(new ItemStack(block.getType()))
                                .replace("%player%", p.getName()))));
                    }
                }
            }
        }
    }
}