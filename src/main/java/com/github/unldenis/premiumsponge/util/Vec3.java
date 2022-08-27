package com.github.unldenis.premiumsponge.util;

import org.bukkit.Location;
import org.bukkit.World;

public record Vec3(int x, int y, int z) implements Comparable<Vec3> {

  public static final Vec3 REMOVED = new Vec3(Integer.MIN_VALUE, Integer.MIN_VALUE,
      Integer.MIN_VALUE);

  public Vec3(Location location) {
    this(location.getBlockX(), location.getBlockY(), location.getBlockZ());
  }

  public Location asLocation(World world) {
    return new Location(world, x(), y(), z());
  }

  @Override
  public int compareTo(Vec3 o) {
    return Integer.compare(hashCode(), o.hashCode());
  }
}
