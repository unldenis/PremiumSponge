package com.github.unldenis.premiumsponge.recipe;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomRecipe {


  private final Configuration cfg;
  private final int durabilityLevel;

  public CustomRecipe(Configuration cfg) {
    this.cfg = cfg;

    this.durabilityLevel = cfg.getInt("durability-level");
  }

  public void load() {
    addRecipe(
        Material.DIAMOND_HELMET,
        cfg.getString("helmet.name"),
        cfg.getStringList("helmet.lore"),
        "EEE", "E E", "   ");
    addRecipe(
        Material.DIAMOND_CHESTPLATE,
        cfg.getString("chestPlate.name"),
        cfg.getStringList("chestPlate.lore"),
        "E E", "EEE", "EEE");
    addRecipe(
        Material.DIAMOND_LEGGINGS,
        cfg.getString("leggings.name"),
        cfg.getStringList("leggings.lore"),
        "EEE", "E E", "E E");
    addRecipe(
        Material.DIAMOND_BOOTS,
        cfg.getString("boots.name"),
        cfg.getStringList("boots.lore"),
        "   ", "E E", "E E");
  }

  private void addRecipe(Material type, String displayName, List<String> lore, String... shape) {
    ItemStack item = new ItemStack(type);

    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(displayName);
    meta.setLore(lore);

    item.setItemMeta(meta);

    item.addEnchantment(Enchantment.DURABILITY, durabilityLevel);

    ShapedRecipe recipe = new ShapedRecipe(item);
    recipe.shape(shape);
    recipe.setIngredient('E', Material.getMaterial(351), (short) 5);

    Bukkit.addRecipe(recipe);
  }
}
