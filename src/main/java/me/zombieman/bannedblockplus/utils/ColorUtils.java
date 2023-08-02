package me.zombieman.bannedblockplus.utils;

import com.tcoded.legacycolorcodeparser.LegacyColorCodeParser;
import me.zombieman.bannedblockplus.BannedBlockPlus;
import org.bukkit.ChatColor;

public class ColorUtils {

    public ColorUtils(BannedBlockPlus plugin) {}

    public static String color(String string) {
        string = LegacyColorCodeParser.convertHexToLegacy('&', string);
        string = ChatColor.translateAlternateColorCodes('&', string);
        return string;
    }
}