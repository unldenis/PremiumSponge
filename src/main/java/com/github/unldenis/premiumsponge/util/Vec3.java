package com.github.unldenis.premiumsponge.util;

import org.bukkit.Location;
import org.bukkit.World;

public class Vec3 implements Comparable<Vec3> {

  private final int x;
  private final int y;
  private final int z;


  public Vec3(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

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

  public int x() {
    return x;
  }

  public int y() {
    return y;
  }

  public int z() {
    return z;
  }
}
