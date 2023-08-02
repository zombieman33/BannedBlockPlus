package me.zombieman.bannedblockplus.commands;

import com.sun.tools.javac.jvm.Items;
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

import java.util.ArrayList;
import java.util.List;

public class AddBlockCommand implements CommandExecutor, TabCompleter {
    private final BannedBlockPlus plugin;
    public AddBlockCommand(BannedBlockPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be run by a player.");
            return true;
        }
        Player p = (Player) sender;

        if (p.hasPermission("bannedblockplus.command.addblock")) {
            SaveBlockData saveBlockData = new SaveBlockData(plugin);
            if (args.length >= 1) {
                String blockName = args[0].toUpperCase();
                ItemStack block = new ItemStack(Material.valueOf(blockName));
                saveBlockData.saveBlockData(block, p);
            } else {
                ItemStack heldItem = p.getInventory().getItemInMainHand();
                saveBlockData.saveBlockData(heldItem, p);
            }
        } else {
            HelpMessages.noPermission(p);
        }
        return false;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        Player player = (Player) sender;

        if (args.length == 1) {
            if (player.hasPermission("bannedblockplus.command.addblock")) {
                for (Material blocks : Material.values()) {
                    if (blocks.isBlock()) {
                        completions.add(blocks.name());
                    }
                }
            }
        }
        return completions;
    }
}
