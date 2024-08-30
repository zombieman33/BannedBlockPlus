package me.zombieman.bannedblockplus.data;

import me.zombieman.bannedblockplus.BannedBlockPlus;
import me.zombieman.bannedblockplus.utils.ColorUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class SaveBlockData {
    private BannedBlockPlus plugin;
    public SaveBlockData(BannedBlockPlus plugin) {
        this.plugin = plugin;
    }

    public void saveBlockData(ItemStack block, Player p) {
        String blockName = block.getType().toString();
        if (block.getType().isBlock() && !block.getType().isAir()) {
            List<String> blocks = plugin.getConfig().getStringList("bannedBlocks");
            if (!blocks.contains(blockName)) {
                blocks.add(blockName);
                plugin.getConfig().set("bannedBlocks", blocks);
                plugin.saveConfig();
                p.sendMessage(ColorUtils.color(plugin.getConfig().getString("addBannedBlockMessage")
                        .replace("%block%", blockName)
                        .replace("%block-name%", this.blockName(block))
                        .replace("%player%", p.getName())));
            } else {
                p.sendMessage(ColorUtils.color("&cThis block is already added to the list of banned blocks."));
            }
        } else {
            p.sendMessage(ColorUtils.color("&cYou can't add " + SaveBlockData.blockName(block) + " as a banned block!"));
        }
    }
    public void removeBlockData(ItemStack block, Player p) {
        String blockName = block.getType().toString();
        String formattedName = blockName.toLowerCase().replace(" ", "_");
        if (block.getType().isBlock()) {
            List<String> blocks = plugin.getConfig().getStringList("bannedBlocks");
            if (blocks.contains(blockName)) {
                blocks.remove(blockName);
                plugin.getConfig().set("bannedBlocks", blocks);
                plugin.saveConfig();
                p.sendMessage(ColorUtils.color(plugin.getConfig().getString("removeBannedBlockMessage")
                        .replace("%block%", blockName)
                        .replace("%block-name%", this.blockName(block))
                        .replace("%player%", p.getName())));
            } else {
                p.sendMessage(ColorUtils.color("&cThis block is not added to the list of banned blocks."));
                TextComponent isnotinlist = new TextComponent(ColorUtils.color("&cTo check what blocks are in the list '/checkbannedblock " + blockName + "'"));
                isnotinlist.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/checkbannedblock " + formattedName));
                isnotinlist.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new ComponentBuilder("Click To Check: " + formattedName).color(net.md_5.bungee.api.ChatColor.GRAY).italic(true).create()));
                p.spigot().sendMessage(isnotinlist);
            }
        } else if (block.getType().isAir()) {
            p.sendMessage(ColorUtils.color("&cYou can't remove air from the list of banned blocks!"));
        } else {
            p.sendMessage(ColorUtils.color("&cThis is not a valid block"));
        }
    }
    public void checkBlockData(ItemStack block, Player p) {
        if (block != null) {
            String blockName = block.getType().toString();
            if (block.getType().isBlock()) {
                List<String> blocks = plugin.getConfig().getStringList("bannedBlocks");
                if (blocks.contains(blockName)) {
                    p.sendMessage(ColorUtils.color(plugin.getConfig().getString("checkBannedBlockMessageSuccess")
                            .replace("%block%", blockName)
                            .replace("%block-name%", blockName(block))
                            .replace("%player%", p.getName())));
                } else {
                    p.sendMessage(ColorUtils.color(plugin.getConfig().getString("checkBannedBlockMessageError")
                            .replace("%block%", blockName)
                            .replace("%block-name%", blockName(block))
                            .replace("%player%", p.getName())));
                }
            } else if (block.getType().isAir()) {
                p.sendMessage(ColorUtils.color("&cYou can't check air!"));
            } else {
                p.sendMessage(ColorUtils.color("&cThis isn't a valid block."));
            }
        } else {
            p.sendMessage(ColorUtils.color("&cThis isn't a valid block."));
        }
    }

    public static String blockName(ItemStack block) {
        String strBlock = block.getType().toString();
        String blockName = strBlock.toLowerCase()
                .replace("_", " ");
        return blockName;
    }
}
