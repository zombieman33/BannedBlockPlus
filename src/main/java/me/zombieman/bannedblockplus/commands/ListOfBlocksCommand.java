package me.zombieman.bannedblockplus.commands;

import me.zombieman.bannedblockplus.BannedBlockPlus;
import me.zombieman.bannedblockplus.utils.ColorUtils;
import me.zombieman.bannedblockplus.utils.HelpMessages;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.C;

import javax.swing.text.html.MinimalHTMLWriter;
import java.awt.*;
import java.util.List;

public class ListOfBlocksCommand implements CommandExecutor {
    private final BannedBlockPlus plugin;
    public ListOfBlocksCommand(BannedBlockPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be run by a player.");
            return true;
        }
        Player p = (Player) sender;

        if (!p.hasPermission("bannedblockplus.command.list")) {
            HelpMessages.noPermission(p);
            return false;
        }
        List<String> blocks = plugin.getConfig().getStringList("bannedBlocks");
        if (blocks.isEmpty()) {
            p.sendMessage(ColorUtils.color("&cThere isn't any banned blocks in this list."));
            return false;
        }

        p.sendMessage(ColorUtils.color("&aBanned Block Plus > Block List"));
        p.sendMessage(" ");
        String blockList = String.join(", ", blocks);
        TextComponent isnotinlist = new TextComponent(ColorUtils.color("&a" + blockList));
        isnotinlist.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/listblocks " + blockList));
        isnotinlist.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("Click To Check: " + blockList).color(net.md_5.bungee.api.ChatColor.GRAY).italic(true).create()));
        p.spigot().sendMessage(isnotinlist);

        p.sendMessage();
        p.sendMessage(" ");
        int amountOfBlocks = blocks.size();
        p.sendMessage(ChatColor.GREEN + "%d Banned Blocks".formatted(amountOfBlocks));
        return false;
    }
}

