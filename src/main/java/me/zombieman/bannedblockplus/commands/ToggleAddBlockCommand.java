package me.zombieman.bannedblockplus.commands;

import me.zombieman.bannedblockplus.BannedBlockPlus;
import me.zombieman.bannedblockplus.data.PlayerData;
import me.zombieman.bannedblockplus.utils.ColorUtils;
import me.zombieman.bannedblockplus.utils.HelpMessages;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ToggleAddBlockCommand implements CommandExecutor {

    private final BannedBlockPlus plugin;

    public ToggleAddBlockCommand(BannedBlockPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be run by a player.");
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission("bannedblockplus.command.toggleaddbannedblock")) {
            HelpMessages.noPermission(p);
            return false;
        }

        PlayerData playerData = new PlayerData(plugin);
        playerData.saveToggleAddBannedBlocks(p);
        return true;
    }
}
