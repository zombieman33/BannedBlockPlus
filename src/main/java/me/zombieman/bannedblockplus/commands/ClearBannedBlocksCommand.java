package me.zombieman.bannedblockplus.commands;

import me.zombieman.bannedblockplus.BannedBlockPlus;
import me.zombieman.bannedblockplus.data.SaveBlockData;
import me.zombieman.bannedblockplus.utils.ColorUtils;
import me.zombieman.bannedblockplus.utils.HelpMessages;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ClearBannedBlocksCommand implements CommandExecutor {
    private final BannedBlockPlus plugin;
    public ClearBannedBlocksCommand(BannedBlockPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be run by a player.");
            return true;
        }
        Player p = (Player) sender;

        if (p.hasPermission("bannedblockplus.command.clearblocks")) {
            List<String> bannedBlocks = plugin.getConfig().getStringList("bannedBlocks");
            if (bannedBlocks.isEmpty()) {
                p.sendMessage(ColorUtils.color("&cThere isn't any blocks in the list of banned blocks."));
            } else {
                int size = bannedBlocks.size();
                bannedBlocks.clear();
                plugin.getConfig().set("bannedBlocks", bannedBlocks);
                plugin.saveConfig();
                p.sendMessage(ColorUtils.color("&aSuccessfully cleared x" + size + " banned blocks."));
            }
        } else {
            HelpMessages.noPermission(p);
        }
        return false;
    }
}