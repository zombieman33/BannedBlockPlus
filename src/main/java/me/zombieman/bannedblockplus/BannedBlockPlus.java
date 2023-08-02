package me.zombieman.bannedblockplus;

import me.zombieman.bannedblockplus.commands.*;
import me.zombieman.bannedblockplus.data.BlockData;
import me.zombieman.bannedblockplus.data.SaveBlockData;
import me.zombieman.bannedblockplus.data.PlayerData;
import me.zombieman.bannedblockplus.listeners.BlockBreakListener;
import me.zombieman.bannedblockplus.listeners.BlockPlaceListener;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class BannedBlockPlus extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            getLogger().info("Config file not found, creating...");
            saveResource("config.yml", false);
        }
        File playerDataFile = new File(getDataFolder(), "playerData.yml");
        if (!playerDataFile.exists()) {
            getLogger().info("PlayerData file not found, creating...");
            saveResource("playerData.yml", false);
        }
        // Commands
        AddBlockCommand addBlockCommand = new AddBlockCommand(this);
        PluginCommand addBlockCmd = getCommand("addbannedblock");
        if (addBlockCmd != null) addBlockCmd.setExecutor(addBlockCommand);
        RemoveBlockCommand removeBlockCommand = new RemoveBlockCommand(this);
        PluginCommand removeBlockCmd = getCommand("removebannedblock");
        if (removeBlockCmd != null) removeBlockCmd.setExecutor(removeBlockCommand);
        CheckBlockCommand checkBlockCommand = new CheckBlockCommand(this);
        PluginCommand checkBlockCmd = getCommand("checkbannedblock");
        if (checkBlockCmd != null) checkBlockCmd.setExecutor(checkBlockCommand);
        ToggleBypassCommand ToggleBypassCommand = new ToggleBypassCommand(this);
        PluginCommand toggleBypassCmd = getCommand("bypassbannedblock");
        if (toggleBypassCmd != null) toggleBypassCmd.setExecutor(ToggleBypassCommand);
        ListOfBlocksCommand listOfBlocksCommand = new ListOfBlocksCommand(this);
        PluginCommand listOfBlockCmd = getCommand("listbannedblock");
        if (listOfBlockCmd != null) listOfBlockCmd.setExecutor(listOfBlocksCommand);

        // Data
        new BlockData(this);
        new PlayerData(this);
        new SaveBlockData(this);

        new BlockBreakListener(this);
        new BlockPlaceListener(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}