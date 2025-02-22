package me.zombieman.bannedblockplus;

import me.zombieman.bannedblockplus.commands.*;
import me.zombieman.bannedblockplus.data.BlockData;
import me.zombieman.bannedblockplus.data.SaveBlockData;
import me.zombieman.bannedblockplus.data.PlayerData;
import me.zombieman.bannedblockplus.listeners.BlockBreakListener;
import me.zombieman.bannedblockplus.listeners.BlockDestroyListener;
import me.zombieman.bannedblockplus.listeners.BlockPlaceListener;
import me.zombieman.bannedblockplus.managers.PlayerDataManager;
import me.zombieman.bannedblockplus.managers.RegionManager;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class BannedBlockPlus extends JavaPlugin {

    public RegionManager regionManager;

    @Override
    public void onEnable() {
        // Plugin startup logic

        PlayerDataManager.initDataFolder(this);

        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            getLogger().info("Config file not found, creating...");
            saveResource("config.yml", false);
        }


        // Check for WorldGuard
        if (getServer().getPluginManager().getPlugin("WorldGuard") == null) {
            getLogger().warning("-----------------------------------------");
            getLogger().warning("WARNING");
            getLogger().warning("WorldGuard plugin is not installed!");
            getLogger().warning("BannedBlockPlus is now being disabled!");
            getLogger().warning("-----------------------------------------");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // Commands
        AddBlockCommand addBlockCommand = new AddBlockCommand(this);
        PluginCommand addBlockCmd = getCommand("addbannedblocks");
        if (addBlockCmd != null) addBlockCmd.setExecutor(addBlockCommand);

        AddAllBlocksCmd addAllBlocksCommand = new AddAllBlocksCmd(this);
        PluginCommand addAllBlocksCmd = getCommand("addallblocks");
        if (addAllBlocksCmd != null) addAllBlocksCmd.setExecutor(addAllBlocksCommand);

        RemoveBlockCommand removeBlockCommand = new RemoveBlockCommand(this);
        PluginCommand removeBlockCmd = getCommand("removebannedblocks");
        if (removeBlockCmd != null) removeBlockCmd.setExecutor(removeBlockCommand);

        CheckBlockCommand checkBlockCommand = new CheckBlockCommand(this);
        PluginCommand checkBlockCmd = getCommand("checkbannedblocks");
        if (checkBlockCmd != null) checkBlockCmd.setExecutor(checkBlockCommand);

        ToggleBypassCommand ToggleBypassCommand = new ToggleBypassCommand(this);
        PluginCommand toggleBypassCmd = getCommand("bypassbannedblocks");
        if (toggleBypassCmd != null) toggleBypassCmd.setExecutor(ToggleBypassCommand);

        ToggleAddBlockCommand toggleAddBlockCommand = new ToggleAddBlockCommand(this);
        PluginCommand toggleAddBlockPlCmd = getCommand("toggleaddbannedblocks");
        if (toggleAddBlockPlCmd != null) toggleAddBlockPlCmd.setExecutor(toggleAddBlockCommand);

        ListOfBlocksCommand listOfBlocksCommand = new ListOfBlocksCommand(this);
        PluginCommand listOfBlockCmd = getCommand("listbannedblocks");
        if (listOfBlockCmd != null) listOfBlockCmd.setExecutor(listOfBlocksCommand);

        ReloadConfigCommand reloadConfigCommand = new ReloadConfigCommand(this);
        PluginCommand reloadConfigCmd = getCommand("reloadbannedblocksconfig");
        if (reloadConfigCmd != null) reloadConfigCmd.setExecutor(reloadConfigCommand);

        ClearBannedBlocksCommand clearBannedBlocksCommand = new ClearBannedBlocksCommand(this);
        PluginCommand clearBannedBlocksCmd = getCommand("clearlistofbannedblocks");
        if (clearBannedBlocksCmd != null) clearBannedBlocksCmd.setExecutor(clearBannedBlocksCommand);

        // Data
        new BlockData(this);
        new PlayerData(this);
        new SaveBlockData(this);

        new BlockBreakListener(this);
        new BlockPlaceListener(this);
        new BlockDestroyListener(this);

        // Managers
        new PlayerDataManager();

        regionManager = new RegionManager();

    }

    public RegionManager getRegionManager() {
        return regionManager;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}