package me.zombieman.bannedblockplus.commands;

import me.zombieman.bannedblockplus.BannedBlockPlus;
import me.zombieman.bannedblockplus.data.PlayerData;
import me.zombieman.bannedblockplus.utils.ColorUtils;
import me.zombieman.bannedblockplus.utils.HelpMessages;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleBypassCommand implements CommandExecutor {
    private final BannedBlockPlus plugin;
    public ToggleBypassCommand(BannedBlockPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be run by a player.");
            return true;
        }
        Player p = (Player) sender;

        if (!p.hasPermission("bannedblockplus.command.togglebypass")) {
            HelpMessages.noPermission(p);
            return false;
        }
        PlayerData playerData = new PlayerData(plugin);
        if (args.length >= 1) {
            String targetName = args[0];
            Player target = Bukkit.getPlayerExact(targetName);
            if (target == null) {
                p.sendMessage(ColorUtils.color("&c'" + targetName + "' dose not exist."));
                return false;
            }

            playerData.savePlayer(target);
            if (target != p) {
                playerData.checkPlayer(target, p);
            }

        } else {
            playerData.savePlayer(p);
        }

        return false;
    }
}
