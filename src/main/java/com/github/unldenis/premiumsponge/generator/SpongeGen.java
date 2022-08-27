package com.github.unldenis.premiumsponge.generator;

import com.github.unldenis.premiumsponge.PremiumSponge;
import com.github.unldenis.premiumsponge.task.PlaceableBlock;
import com.github.unldenis.premiumsponge.util.Vec3;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import javax.swing.plaf.PanelUI;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public final class SpongeGen {

  private final World world;
  private final int radius;
  private final int min_depth;
  private final Set<Vec3> sponges;
  private final int number_of_sponges1;

  public SpongeGen(World world, int radius, int min_depth, int number_of_sponges) {
    this.world = world;
    this.radius = radius;
    this.min_depth = min_depth;

    if (number_of_sponges == -1) {
      number_of_sponges1 = (int) (100D / ThreadLocalRandom.current().nextDouble(4, 7));
    } else {
      number_of_sponges1 = number_of_sponges;
    }
    this.sponges = new HashSet<>(number_of_sponges1);
  }


  private void place(Vec3 loc) {
//    world.getBlockAt(loc.x(), loc.y(), loc.z()).setType(Material.SPONGE, true);
    var thread = PremiumSponge.getInstance().workloadThread();
    thread.addLoad(
        new PlaceableBlock(world, loc, Material.SPONGE));
  }

  private Vec3 random(Vec3 center, int randomX, int randomZ) {
    int x = center.x() + randomX;
    int z = center.z() + randomZ;
    return new Vec3(
        x,
        world.getHighestBlockYAt(x, z),
        z);
  }

  public void start() {
    var wg = PremiumSponge.getInstance().worldGuardSupport();
    var logger = PremiumSponge.getInstance().getLogger();
    logger.info("Generating the sponges...");
    var center = new Vec3(world.getSpawnLocation());

    //generate 2 * number_of_sponges1 random number between -radius to radius
    int[] randomIntsArray = IntStream.generate(() -> ThreadLocalRandom.current().nextInt(-radius, radius)).limit(number_of_sponges1 * 2L).toArray();

    for (int j = 0, random = 0; j < number_of_sponges1; j++, random+=2) {
      Vec3 vec = null;
      boolean found = true;
      do {
        if(!found) {
          found = true;
          vec = random(center, ThreadLocalRandom.current().nextInt(-radius, radius), ThreadLocalRandom.current().nextInt(-radius, radius));
        } else {
          vec = random(center, randomIntsArray[random], randomIntsArray[random + 1]);
        }
        if (vec.y() < min_depth) {
          found = false;
          continue;
        }
        if (isWater(world.getBlockAt(vec.x(), vec.y() - 2, vec.z()))) {
          found = false;
          continue;
        }

        if (isPresent(vec.x(), vec.y(), vec.z()) != null) {
          found = false;
          continue;
        }

        if(!wg.isFree(vec,world)) {
          found = false;
        }

      } while (!found);

      place(vec);
      sponges.add(vec);

      double percent = Math.floor(((double) (j * 100) / number_of_sponges1));
      if (percent % 10 == 0) {
        logger.info(percent + "%");
      }
    }

    logger.info("...Done");
  }

  public Vec3 isPresent(int x, int y, int z) {
    for (var vec : sponges) {
      if (vec.x() == x && vec.y() == y && vec.z() == z) {
        return vec;
      }
    }
    return null;
  }

  public boolean remove(Vec3 vec3) {
    return sponges.remove(vec3);
  }

  public void removeAll() {
    if (sponges == null) {
      return;
    }
    var thread = PremiumSponge.getInstance().workloadThread();
    for (var vec : sponges) {
      if (vec != Vec3.REMOVED) {
//        world.getBlockAt(vec.x(), vec.y(), vec.z()).setType(Material.AIR);
        thread.addLoad(
            new PlaceableBlock(world, vec, Material.AIR));
      }
    }
  }

  private boolean isWater(Block block) {
    return block.getType().equals(Material.WATER) | block.getType()
        .equals(Material.STATIONARY_WATER);
  }

  private boolean isAir(Block block) {
    return block.getType().equals(Material.ARROW);
  }
}
