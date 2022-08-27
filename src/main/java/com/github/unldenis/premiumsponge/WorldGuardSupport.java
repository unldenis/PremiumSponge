package com.github.unldenis.premiumsponge;

import com.github.unldenis.premiumsponge.util.Vec3;
import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.bukkit.RegionQuery;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import org.bukkit.World;

public class WorldGuardSupport {

  private final RegionContainer container;

  public WorldGuardSupport() {
    WorldGuardPlugin worldGuardPlugin = WGBukkit.getPlugin();
    container = worldGuardPlugin.getRegionContainer();
  }

  public boolean isFree(Vec3 vec3, World world) {
    RegionQuery query = container.createQuery();
    ApplicableRegionSet set = query.getApplicableRegions(vec3.asLocation(world));
    return set.size() == 0;
  }
}
