package com.github.unldenis.premiumsponge.data;

import org.bukkit.configuration.*;
import org.bukkit.configuration.file.*;
import org.bukkit.plugin.java.*;

import java.io.*;
import java.util.logging.*;

public class DataManager {

  private final JavaPlugin plugin;
  private final String nameFile;

  private FileConfiguration dataConfig;
  private File configFile;

  public DataManager(JavaPlugin plugin, String nameFile) {
    this.dataConfig = null;
    this.configFile = null;
    this.plugin = plugin;
    this.nameFile = nameFile;
    this.saveDefaultConfig();
  }

  public void reloadConfig() {
    if (this.configFile == null) {
      this.configFile = new File(this.plugin.getDataFolder(), this.nameFile);
    }
    this.dataConfig = (FileConfiguration) YamlConfiguration.loadConfiguration(this.configFile);
    final InputStream defaultStream = this.plugin.getResource(this.nameFile);
    if (defaultStream != null) {
      final YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(
          (Reader) new InputStreamReader(defaultStream));
      this.dataConfig.setDefaults((Configuration) defaultConfig);
    }
  }

  public FileConfiguration getConfig() {
    if (this.dataConfig == null) {
      this.reloadConfig();
    }
    return this.dataConfig;
  }

  public void saveConfig() {
    if (this.dataConfig == null || this.configFile == null) {
      return;
    }
    try {
      this.getConfig().save(this.configFile);
    } catch (IOException e) {
      this.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.configFile, e);
    }
  }

  public void saveDefaultConfig() {
    if (this.configFile == null) {
      this.configFile = new File(this.plugin.getDataFolder(), this.nameFile);
    }
    if (!this.configFile.exists()) {
      this.plugin.saveResource(this.nameFile, false);
    }
  }
}