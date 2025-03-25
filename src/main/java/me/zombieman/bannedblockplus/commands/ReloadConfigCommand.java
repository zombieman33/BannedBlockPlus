package me.zombieman.bannedblockplus.commands;

import me.zombieman.bannedblockplus.BannedBlockPlus;
import me.zombieman.bannedblockplus.utils.ColorUtils;
import me.zombieman.bannedblockplus.utils.HelpMessages;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.List;

public class ReloadConfigCommand implements CommandExecutor {
    private final BannedBlockPlus plugin;
    public ReloadConfigCommand(BannedBlockPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be run by a player.");
            return true;
        }
        Player p = (Player) sender;

        if (!p.hasPermission("bannedblockplus.command.reloadconfig")) {
            HelpMessages.noPermission(p);
            return false;
        }

        try {
            plugin.reloadConfig();
            p.sendMessage(ColorUtils.color("&aConfig reloaded successfully!"));
        } catch (Exception e) {
            p.sendMessage(ColorUtils.color("&cAn error occurred while reloading the plugin: " + e.getMessage()));
        }
        return false;
    }
}