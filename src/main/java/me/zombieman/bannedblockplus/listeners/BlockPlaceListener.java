package me.zombieman.bannedblockplus.listeners;

import me.zombieman.bannedblockplus.BannedBlockPlus;
import me.zombieman.bannedblockplus.commands.ToggleAddBlockCommand;
import me.zombieman.bannedblockplus.data.BlockData;
import me.zombieman.bannedblockplus.data.PlayerData;
import me.zombieman.bannedblockplus.data.SaveBlockData;
import me.zombieman.bannedblockplus.managers.PlayerDataManager;
import me.zombieman.bannedblockplus.utils.ColorUtils;
import me.zombieman.bannedblockplus.utils.ReplaceString;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
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
        if (!blocks.contains(block.getType().toString())) return;
        UUID pUUID = p.getUniqueId();

        boolean wantsToBypass = PlayerDataManager.getPlayerDataConfig(plugin, pUUID).getBoolean(pUUID + ".enabled", false);
        if (wantsToBypass) return;

        if (plugin.getRegionManager().isInRegion(plugin, block.getLocation())) return;

        if (e.isCancelled()) return;

        e.setCancelled(true);

        if (plugin.getConfig().getBoolean("message.shouldSendTheSameMessage")) {
            String formatted = ReplaceString.replace(plugin.getConfig().getString("message.bannedBlockMessage"), block, p);
            p.sendMessage(ColorUtils.color(formatted));
            return;
        }

        String formatted = ReplaceString.replace(plugin.getConfig().getString("message.bannedBlockMessagePlace"), block, p);
        p.sendMessage(ColorUtils.color(formatted));
    }


    @EventHandler(priority = EventPriority.LOW)
    public void onBreakToggledAddBannedBlock(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        PlayerData playerData = new PlayerData(plugin);
        if (!playerData.checkToggleBlocks(player)) return;

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
