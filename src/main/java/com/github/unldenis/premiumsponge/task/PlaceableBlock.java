package com.github.unldenis.premiumsponge.task;

import com.github.unldenis.premiumsponge.util.Vec3;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.IBlockData;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

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
//    world.getBlockAt(vec3.x(), vec3.y(), vec3.z()).setType(type);

    setBlockInNativeWorld(world, vec3.x(), vec3.y(), vec3.z(), type.getId(), (byte) 0, true);
  }
  private static void setBlockInNativeWorld(World world, int x, int y, int z, int blockId, byte data, boolean applyPhysics) {
    net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) world).getHandle();
    BlockPosition bp = new BlockPosition(x, y, z);
    IBlockData ibd = net.minecraft.server.v1_8_R3.Block.getByCombinedId(blockId + (data << 12));
    nmsWorld.setTypeAndData(bp, ibd, applyPhysics ? 3 : 2);
  }
}