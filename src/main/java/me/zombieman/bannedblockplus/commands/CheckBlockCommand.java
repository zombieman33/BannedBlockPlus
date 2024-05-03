package me.zombieman.bannedblockplus.commands;

import me.zombieman.bannedblockplus.BannedBlockPlus;
import me.zombieman.bannedblockplus.data.SaveBlockData;
import me.zombieman.bannedblockplus.utils.ColorUtils;
import me.zombieman.bannedblockplus.utils.HelpMessages;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CheckBlockCommand implements CommandExecutor, TabCompleter {
    private final BannedBlockPlus plugin;
    public CheckBlockCommand(BannedBlockPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be run by a player.");
            return true;
        }
        Player p = (Player) sender;

        if (p.hasPermission("bannedblockplus.command.checkblock")) {
            ItemStack heldItem = p.getInventory().getItemInMainHand();
            SaveBlockData saveBlockData = new SaveBlockData(plugin);
            if (args.length >= 1) {
                String itemName = args[0].toUpperCase();
                ItemStack itemStack = new ItemStack(Material.valueOf(itemName.toUpperCase()));
                saveBlockData.checkBlockData(itemStack, p);
            } else {
                if (heldItem.getType().isBlock()) {
                    saveBlockData.checkBlockData(heldItem, p);
                } else {
                    p.sendMessage(ColorUtils.color("&6/checkblock"));
                }
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
            if (player.hasPermission("bannedblockplus.command.checkblock.block")) {
                for (Material blocks : Material.values()) {
                    if (blocks.isBlock()) {
                        completions.add(blocks.name().toLowerCase());
                    }
                }
//                completions.add("grass block");
//                completions.add("GRASS BLOCK");
//                completions.add("GRASS_BLOCK");
            }
            if (player.hasPermission("bannedblockplus.command.checkblock.player")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    String pName = p.getName();
                    completions.add(pName.toLowerCase());
                }
            }
        }
        String lastArg = args[args.length - 1];
        return completions.stream().filter(s -> s.startsWith(lastArg)).collect(Collectors.toList());
    }
}
