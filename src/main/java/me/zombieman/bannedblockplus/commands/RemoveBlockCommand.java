package me.zombieman.bannedblockplus.commands;

import me.zombieman.bannedblockplus.BannedBlockPlus;
import me.zombieman.bannedblockplus.data.SaveBlockData;
import me.zombieman.bannedblockplus.utils.ColorUtils;
import me.zombieman.bannedblockplus.utils.HelpMessages;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveBlockCommand implements CommandExecutor, TabCompleter {
    private final BannedBlockPlus plugin;
    public RemoveBlockCommand(BannedBlockPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be run by a player.");
            return true;
        }
        Player p = (Player) sender;

        if (p.hasPermission("bannedblockplus.command.removeblock")) {
            SaveBlockData saveBlockData = new SaveBlockData(plugin);
            if (args.length >= 1) {
                String blockName = args[0].toUpperCase();
                ItemStack block = new ItemStack(Material.valueOf(blockName.toUpperCase()));
                saveBlockData.removeBlockData(block, p);
            } else {
                ItemStack heldItem = p.getInventory().getItemInMainHand();
                saveBlockData.removeBlockData(heldItem, p);
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
        if (player.hasPermission("bannedblockplus.command.removeblock")) {
            if (args.length == 1) {
                List<String> blocks = plugin.getConfig().getStringList("bannedBlocks");
                completions.addAll(blocks);
            }
        }

        String lastArg = args[args.length - 1];
        return completions.stream().filter(s -> s.startsWith(lastArg)).collect(Collectors.toList());
    }
}