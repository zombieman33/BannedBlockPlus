package me.zombieman.bannedblockplus.data;

import me.zombieman.bannedblockplus.BannedBlockPlus;
import me.zombieman.bannedblockplus.managers.PlayerDataManager;
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
        UUID pUUID = p.getUniqueId();
        boolean hasEnabled = PlayerDataManager.getPlayerDataConfig(plugin, pUUID).getBoolean(pUUID + ".enabled", false);
        if (hasEnabled) {
            PlayerDataManager.getPlayerDataConfig(plugin, pUUID).set(pUUID + ".enabled", false);
            removePlayer(p, "&aBypass Banned Blocks &f[&cDisabled&f]");
        } else {
            PlayerDataManager.getPlayerDataConfig(plugin, pUUID).set(pUUID + ".enabled", true);
            p.sendMessage(ColorUtils.color("&aBypass Banned Blocks &f[&aEnabled&f]"));
        }
        PlayerDataManager.savePlayerData(plugin, p);
    }

    public void removePlayer(Player p, String message) {
        UUID pUUID = p.getUniqueId();
        PlayerDataManager.getPlayerDataConfig(plugin, pUUID).set(pUUID + ".enabled", false);
        PlayerDataManager.savePlayerData(plugin, p);
        p.sendMessage(ColorUtils.color(message));
    }

    public void forceSave(Player p, boolean enable) {
        UUID pUUID = p.getUniqueId();
        PlayerDataManager.getPlayerDataConfig(plugin, pUUID).set(pUUID + ".enabled", enable);
        PlayerDataManager.savePlayerData(plugin, p);
    }

    public void checkPlayer(Player p, Player toSend) {
        UUID pUUID = p.getUniqueId();
        boolean hasEnabled = PlayerDataManager.getPlayerDataConfig(plugin, pUUID).getBoolean(pUUID + ".enabled", false);
        if (hasEnabled) {
            toSend.sendMessage(ColorUtils.color("&aBypass Banned Blocks &f[&cDisabled&f] &afor: " + p.getName()));
            return;
        }
        toSend.sendMessage(ColorUtils.color("&aBypass Banned Blocks &f[&aEnabled&f] &afor: " + p.getName()));
        if (!toSend.hasPermission("bannedblockplus.command.bypass")) {
            toSend.sendMessage(ColorUtils.color("&c'" + p.getName() + "' does not have permission to have this enabled, disabling."));
            toSend.sendMessage(ColorUtils.color("&c'" + p.getName() + "' needs the permission 'bannedblockplus.command.bypass'"));
            this.forceSave(p, false);
        }
    }

    public void saveToggleAddBannedBlocks(Player p) {
        UUID pUUID = p.getUniqueId();
        boolean hasEnabled = PlayerDataManager.getPlayerDataConfig(plugin, pUUID).getBoolean(pUUID + ".toggleaddblocks", false);
        if (hasEnabled) {
            PlayerDataManager.getPlayerDataConfig(plugin, pUUID).set(pUUID + ".toggleaddblocks", false);
            removePlayer(p, "&aToggle Add Banned Blocks &f[&cDisabled&f]");
        } else {
            PlayerDataManager.getPlayerDataConfig(plugin, pUUID).set(pUUID + ".toggleaddblocks", true);
            p.sendMessage(ColorUtils.color("&aToggle Add Banned Blocks &f[&aEnabled&f]"));
        }
        PlayerDataManager.savePlayerData(plugin, p);
    }
    public boolean checkToggleBlocks(Player p) {
        UUID pUUID = p.getUniqueId();
        return PlayerDataManager.getPlayerDataConfig(plugin, pUUID).getBoolean(pUUID + ".toggleaddblocks", false);
    }
}