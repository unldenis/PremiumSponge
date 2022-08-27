package com.github.unldenis.premiumsponge.generator;

import com.github.unldenis.premiumsponge.util.Vec3;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public final class SpongeGen {

  private final World world;
  private final int radius;
  private final int min_depth;
  private final Vec3[] sponges;

  public SpongeGen(World world, int radius, int min_depth, int number_of_sponges) {
    this.world = world;
    this.radius = radius;
    this.min_depth = min_depth;
    int number_of_sponges1;
    if (number_of_sponges == -1) {
      number_of_sponges1 = (int) (100D / ThreadLocalRandom.current().nextDouble(4, 7));
    } else {
      number_of_sponges1 = number_of_sponges;
    }
    this.sponges = new Vec3[number_of_sponges1];
  }


  private void place(Vec3 loc) {
    world.getBlockAt(loc.x(), loc.y(), loc.z()).setType(Material.SPONGE, true);
  }

  private Vec3 spawn(Vec3 center) {

    return new Vec3(
        center.x() + ThreadLocalRandom.current().nextInt(-radius, radius),
        center.y(),
        center.z() + ThreadLocalRandom.current().nextInt(-radius, radius));
  }

  public void start() {
    var center = new Vec3(world.getSpawnLocation());

    for (int j = 0; j < sponges.length; j++) {
      Vec3 vec = null;
      do {
        vec = spawn(center);
        if (world.getBlockAt(vec.x(), vec.y() - 1, vec.z()).getType() == Material.AIR) {
          vec = new Vec3(vec.x(), vec.y() - 1, vec.z());
        }
      } while (vec.y() < min_depth || isWater(world.getBlockAt(vec.x(), vec.y() - 1, vec.z())));
      place(vec);
      sponges[j] = vec;
    }

    Arrays.sort(sponges);
  }

  public Vec3[] sponges() {
    return sponges;
  }

  public int isPresent(Vec3 vec3) {
    return Arrays.binarySearch(sponges, vec3);
  }

  public void remove(int index) {
    sponges[index] = Vec3.REMOVED;
  }

  public void removeAll() {
    for (var vec : sponges) {
      if (vec != Vec3.REMOVED) {
        world.getBlockAt(vec.x(), vec.y(), vec.z()).setType(Material.AIR);
      }
    }
  }

  private boolean isWater(Block block) {
    return block.getType().equals(Material.WATER) | block.getType()
        .equals(Material.STATIONARY_WATER);
  }
}
