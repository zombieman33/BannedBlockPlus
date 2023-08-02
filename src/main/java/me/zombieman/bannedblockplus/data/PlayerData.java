package me.zombieman.bannedblockplus.data;

import me.zombieman.bannedblockplus.BannedBlockPlus;
import me.zombieman.bannedblockplus.utils.ColorUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.UUID;

public class PlayerData {
    private BannedBlockPlus plugin;
    public PlayerData(BannedBlockPlus plugin) {
        this.plugin = plugin;
    }
    public void savePlayer(Player p) {
        File playerDataFile = new File(plugin.getDataFolder(), "playerData.yml");
        FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
        UUID pUUID = p.getUniqueId();
        boolean hasEnabled = playerDataConfig.getBoolean(pUUID + ".enabled", false);
        if (hasEnabled) {
            playerDataConfig.set(pUUID + ".enabled", false);
            removePlayer(p, "&aBypass Banned Blocks &f[&cDisabled&f]");
        } else {
            playerDataConfig.set(pUUID + ".enabled", true);
            p.sendMessage(ColorUtils.color("&aBypass Banned Blocks &f[&aEnabled&f]"));
        }
        SaveData.saveData(playerDataConfig, playerDataFile);
    }
    public void removePlayer(Player p, String message) {
        File playerDataFile = new File(plugin.getDataFolder(), "playerData.yml");
        FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
        UUID pUUID = p.getUniqueId();
        playerDataConfig.set(pUUID + ".enabled", false);
        plugin.saveConfig();
        p.sendMessage(ColorUtils.color(message));
    }

    public void forceSave(Player p, boolean enable) {
        File playerDataFile = new File(plugin.getDataFolder(), "playerData.yml");
        FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
        UUID pUUID = p.getUniqueId();
        playerDataConfig.set(pUUID + ".enabled", enable);
        SaveData.saveData(playerDataConfig, playerDataFile);
    }

    public void checkPlayer(Player p, Player toSend) {
        File playerDataFile = new File(plugin.getDataFolder(), "playerData.yml");
        FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
        UUID pUUID = p.getUniqueId();
        boolean hasEnabled = playerDataConfig.getBoolean(pUUID + ".enabled", false);
        if (hasEnabled) {
            toSend.sendMessage(ColorUtils.color("&aBypass Banned Blocks &f[&cDisabled&f] &afor: " + p.getName()));
        } else {
            toSend.sendMessage(ColorUtils.color("&aBypass Banned Blocks &f[&aEnabled&f] &afor: " + p.getName()));
            if (!toSend.hasPermission("bannedblockplus.command.bypass")) {
                toSend.sendMessage(ColorUtils.color("&c'" + p.getName() + "' does not have permission to have this enabled, disabling."));
                toSend.sendMessage(ColorUtils.color("&c'" + p.getName() + "' needs the permission 'bannedblockplus.command.bypass'"));
                this.forceSave(p, false);
            }
        }
    }
}
