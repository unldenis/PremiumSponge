package com.github.unldenis.premiumsponge.task;

import com.github.unldenis.premiumsponge.util.Vec3;
import org.bukkit.*;

public record PlaceableBlock(World world, Vec3 vec3, Material type) implements Workload {

  @Override
  public void compute() {
    world.getBlockAt(vec3.x(), vec3.y(), vec3.z()).setType(type);
  }
}