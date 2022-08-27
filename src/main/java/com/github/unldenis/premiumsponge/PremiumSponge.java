package com.github.unldenis.premiumsponge;


import com.github.unldenis.premiumsponge.command.RemoveSpongesCommand;
import com.github.unldenis.premiumsponge.data.DataManager;
import com.github.unldenis.premiumsponge.generator.SpongeGen;
import com.github.unldenis.premiumsponge.listener.SpongeListener;
import com.github.unldenis.premiumsponge.recipe.CustomRecipe;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class PremiumSponge extends JavaPlugin {

  private static PremiumSponge INSTANCE;
  private final AtomicBoolean disabled = new AtomicBoolean(false);
  private SpongeGen gen;
  private String wipeName;
  private List<String> wipeLore;

  public static PremiumSponge getInstance() {
    return INSTANCE;
  }

  @Override
  public void onEnable() {
    // Plugin startup logic
    INSTANCE = this;

    var config = new DataManager(this, "config.yml");

    var cfg = config.getConfig();

    wipeName = cfg.getString("wipe.name");
    wipeLore = cfg.getStringList("wipe.lore");

    gen = new SpongeGen(Bukkit.getWorld("world"), cfg.getInt("radius-spawn"),
        cfg.getInt("min-depth"), cfg.getInt("number-of-sponges"));
    gen.start();

    new SpongeListener();

    this.getCommand("removesponges").setExecutor(new RemoveSpongesCommand());

    new CustomRecipe(cfg).load();
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
    if (disabled().compareAndSet(false, true)) {
      gen().removeAll();
      getLogger().info(ChatColor.LIGHT_PURPLE + "Removed sponges");
    } else {
      getLogger().info(ChatColor.RED + "Sponges already removed");
    }
  }

  public SpongeGen gen() {
    return gen;
  }

  public AtomicBoolean disabled() {
    return disabled;
  }

  public String wipeName() {
    return wipeName;
  }

  public List<String> wipeLore() {
    return wipeLore;
  }
}

