package com.github.unldenis.premiumsponge.listener;

import com.github.unldenis.premiumsponge.PremiumSponge;
import com.github.unldenis.premiumsponge.util.Vec3;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class SpongeListener implements Listener {

  public SpongeListener() {
    Bukkit.getPluginManager().registerEvents(this, PremiumSponge.getInstance());
  }


  @EventHandler
  public void onBreak(BlockBreakEvent event) {
    var type = event.getBlock().getType();
    if (type == Material.SPONGE) {
      var loc = event.getBlock().getLocation();

      var plugin = PremiumSponge.getInstance();
      var gen = plugin.gen();
      var vec = gen.isPresent(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
      if (vec != null) {
        event.setCancelled(true);
        event.getBlock().setType(Material.AIR);

        gen.remove(vec);

        var item = new ItemStack(351, 1, (short) 5);
        var meta = item.getItemMeta();
        meta.setDisplayName(plugin.wipeName());
        meta.setLore(plugin.wipeLore());
        item.setItemMeta(meta);
        loc.getWorld().dropItem(loc, item);
      }
    }
  }

  @EventHandler
  public void craftItem(PrepareItemCraftEvent e) {
    Material type = e.getRecipe().getResult().getType();
    if (type == Material.SPONGE || type.getId() == 351) {
      e.getInventory().setResult(new ItemStack(Material.AIR));
      for (HumanEntity he : e.getViewers()) {
        if (he instanceof Player) {
          he.sendMessage(ChatColor.DARK_RED + "You cannot craft this!");
        }
      }
    }
  }

//  @EventHandler()
//  public void onSpongePlace(BlockPlaceEvent event) {
//    if (event.getBlock().getType() == Material.SPONGE) {
//      var loc = event.getBlock().getLocation();
//
////      System.out.println(event.getBlock().getType().name());
////XIT:  for(int x = -1; x <= 1; x++) {
////        for(int y = -1; y <= 1; y++) {
////          for(int z = -1; z <= 1; z++) {
////            var b = event.getBlock().getWorld().getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
//////            b.setType(Material.GLASS);
////            if(b.getType() == Material.WATER  || b.getType() == Material.STATIONARY_WATER) {
////              event.setCancelled(true);
////              break XIT;
////            }
////          }
////        }
////      }
//
//      loc.setX(loc.getX() - 1);
//      if (isWater(loc)) {
//        replace(event, event.getBlock());
//        return;
//      }
//
//      loc.setX(loc.getX() + 2);
//      if (isWater(loc)) {
//        replace(event, event.getBlock());
//        return;
//      }
//
//      loc.setZ(loc.getZ() - 1);
//      if (isWater(loc)) {
//        replace(event, event.getBlock());
//        return;
//      }
//
//      loc.setZ(loc.getZ() + 2);
//      if (isWater(loc)) {
//        replace(event, event.getBlock());
//      }
//
//    }
//
//  }

//  private void replace(BlockPlaceEvent event, Block block) {
////    event.setCancelled(true);
////    Bukkit.getScheduler().runTaskLater(PremiumSponge.getInstance(), () -> {
////      block.setType(Material.SPONGE);
////    }, 1L);
//
//    event.setCancelled(true);
//    Bukkit.getScheduler().runTaskLater(PremiumSponge.getInstance(), () -> {
//      Material material = Material.getMaterial(Integer.parseInt("19:1".split(":")[0]));
//      block.setType(material);
//    }, 1L);
//  }
//
//  private boolean isWater(Location loc) {
//    return loc.getBlock().getType().equals(Material.WATER) | loc.getBlock().getType()
//        .equals(Material.STATIONARY_WATER);
//  }

}
