package me.zombieman.bannedblockplus.commands;

import me.zombieman.bannedblockplus.BannedBlockPlus;
import me.zombieman.bannedblockplus.data.SaveBlockData;
import me.zombieman.bannedblockplus.utils.ColorUtils;
import me.zombieman.bannedblockplus.utils.HelpMessages;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AddAllBlocksCmd implements CommandExecutor {
    private final BannedBlockPlus plugin;
    public AddAllBlocksCmd(BannedBlockPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be run by a player.");
            return true;
        }
        Player player = (Player) sender;

        if (!player.hasPermission("bannedblockplus.command.addallblocks")) {
            HelpMessages.noPermission(player);
            return false;
        }

        List<String> bannedBlocks = plugin.getConfig().getStringList("bannedBlocks");

        int amountOfNewBlocks = 0;

        for (Material blocks : Material.values()) {
            if (blocks.isBlock() && !bannedBlocks.contains(blocks.name())) {
                bannedBlocks.add(blocks.name());
                amountOfNewBlocks++;
            }
        }

        plugin.getConfig().set("bannedBlocks", bannedBlocks);
        plugin.saveConfig();

        player.sendMessage(ColorUtils.color("&aSuccessfully added &e" + amountOfNewBlocks + " &anew blocks to the banned block list!"));
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f);

        return false;
    }
}

