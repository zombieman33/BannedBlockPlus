package me.zombieman.bannedblockplus.listeners;

import me.zombieman.bannedblockplus.BannedBlockPlus;
import me.zombieman.bannedblockplus.data.BlockData;
import me.zombieman.bannedblockplus.data.SaveBlockData;
import me.zombieman.bannedblockplus.utils.ColorUtils;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class BlockPlaceListener implements Listener {
    private BannedBlockPlus plugin;
    public BlockPlaceListener(BannedBlockPlus plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Block block = e.getBlock();
        List<String> blocks = plugin.getConfig().getStringList("bannedBlocks");
        if (blocks.contains(block.getType().toString())) {
            if (!p.hasPermission("bannedblockplus.bypass")) {
                File playerDataFile = new File(plugin.getDataFolder(), "playerData.yml");
                FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
                UUID pUUID = p.getUniqueId();
                boolean wantsToBypass = playerDataConfig.getBoolean(pUUID + ".enabled", false);
                if (!wantsToBypass) {
                    if (!e.isCancelled()) {
                        e.setCancelled(true);
                        p.sendMessage(ColorUtils.color(plugin.getConfig().getString("bannedBlockMessage")
                                .replace("%block%", block.getType().name())
                                .replace("%block-name%", SaveBlockData.blockName(new ItemStack(block.getType())))
                                .replace("%player%", p.getName())));
                    }
                }
            }
        }
    }
}
