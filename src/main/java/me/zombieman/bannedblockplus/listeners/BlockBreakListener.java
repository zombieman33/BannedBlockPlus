package me.zombieman.bannedblockplus.listeners;

import me.zombieman.bannedblockplus.BannedBlockPlus;
import me.zombieman.bannedblockplus.commands.ToggleAddBlockCommand;
import me.zombieman.bannedblockplus.data.BlockData;
import me.zombieman.bannedblockplus.data.SaveBlockData;
import me.zombieman.bannedblockplus.utils.ColorUtils;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class BlockBreakListener implements Listener {

    private BannedBlockPlus plugin;

    public BlockBreakListener(BannedBlockPlus plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onBreakToggledAddBannedBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (ToggleAddBlockCommand.toggledPlayers.contains(player.getUniqueId())) {
            event.setCancelled(true);
            Block block = event.getBlock();
            FileConfiguration config = plugin.getConfig();
            List<String> blocks = config.getStringList("bannedBlocks");

            String blockTypeName = block.getType().toString();
            if (blocks.contains(blockTypeName)) {
                player.sendMessage(ChatColor.RED + "This block is already banned!");
                return;
            }

            blocks.add(blockTypeName);
            config.set("bannedBlocks", blocks);

            player.sendMessage(ChatColor.GREEN + "Added " + blockTypeName + " to the list of banned blocks.");
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block block = e.getBlock();
        List<String> blocks = plugin.getConfig().getStringList("bannedBlocks");
        if (blocks.contains(block.getType().toString())) {
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
//        boolean isCanceled = e.isCancelled();
//        BlockData blockData = new BlockData(plugin);
//        blockData.block(block, p, isCanceled);
    }
}
