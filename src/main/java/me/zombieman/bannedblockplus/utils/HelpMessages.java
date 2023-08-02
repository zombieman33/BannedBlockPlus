package me.zombieman.bannedblockplus.utils;

import me.zombieman.bannedblockplus.BannedBlockPlus;
import org.bukkit.entity.Player;

public class HelpMessages {

    public HelpMessages(BannedBlockPlus plugin) {}
    public static void noPermission(Player p) {
        p.sendMessage(ColorUtils.color("&cYou don't have permission to run this command."));
    }
}