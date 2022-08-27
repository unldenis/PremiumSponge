package com.github.unldenis.premiumsponge.task;

import com.github.unldenis.premiumsponge.util.Vec3;
import org.bukkit.*;

public class PlaceableBlock implements Workload {

  private final World world;
  private final Vec3 vec3;
  private final Material type;

  public PlaceableBlock(World world, Vec3 vec3, Material type) {
    this.world = world;
    this.vec3 = vec3;
    this.type = type;
  }

  @Override
  public void compute() {
    world.getBlockAt(vec3.x(), vec3.y(), vec3.z()).setType(type);
  }
}