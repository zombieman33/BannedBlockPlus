package me.zombieman.bannedblockplus.managers;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.zombieman.bannedblockplus.BannedBlockPlus;
import org.bukkit.Location;

public class RegionManager {

    public boolean isInRegion(BannedBlockPlus plugin, Location location) {
        World worldeditWorld = BukkitAdapter.adapt(location.getWorld());
        com.sk89q.worldguard.protection.managers.RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(worldeditWorld);

        for (String regionName : plugin.getConfig().getStringList("ignoredRegions")) {
            ProtectedRegion region = regionManager.getRegion(regionName);
            if (region == null) return false;
            return region.contains(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        }

        return false;
    }
}