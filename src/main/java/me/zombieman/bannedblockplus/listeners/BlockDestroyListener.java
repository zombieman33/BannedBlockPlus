package me.zombieman.bannedblockplus.listeners;

import me.zombieman.bannedblockplus.BannedBlockPlus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.block.Block;

import java.util.List;

public class BlockDestroyListener implements Listener {

    private final BannedBlockPlus plugin;

    public BlockDestroyListener(BannedBlockPlus plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockExplosion(BlockExplodeEvent event) {
//        System.out.println("BlockExplodeEvent triggered.");
        protectBannedBlocks(event.blockList());
    }

    @EventHandler
    public void onEntityExplosion(EntityExplodeEvent event) {
//        System.out.println("EntityExplodeEvent triggered.");
        protectBannedBlocks(event.blockList());
    }

    @EventHandler
    public void onBlockFromTo(BlockFromToEvent event) {
        Block toBlock = event.getToBlock();

        if (plugin.getConfig().getStringList("bannedBlocks").contains(toBlock.getType().name())) {
//            System.out.println("Water/Lava flow into banned block prevented: " + toBlock.getType() + " at " + toBlock.getLocation());
            event.setCancelled(true);
        }
    }

    private void protectBannedBlocks(List<Block> blocks) {
        List<String> bannedBlockNames = plugin.getConfig().getStringList("bannedBlocks");

        //                System.out.println("Banned block found: " + block.getType() + " at " + block.getLocation());
        blocks.removeIf(block -> bannedBlockNames.contains(block.getType().name()));
    }
}
