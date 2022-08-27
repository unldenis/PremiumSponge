package com.github.unldenis.premiumsponge.command;

import com.github.unldenis.premiumsponge.PremiumSponge;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RemoveSpongesCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender.hasPermission("premiumsponge.admin")) {
      var plugin = PremiumSponge.getInstance();
      if (plugin.disabled().compareAndSet(false, true)) {
        plugin.gen().removeAll();
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "Removed sponges");
      } else {
        sender.sendMessage(ChatColor.RED + "Sponges already removed");
      }
    } else {
      sender.sendMessage(ChatColor.RED + "Permission 'premiumsponge.admin' required");
    }

    // If the player (or console) uses our command correct, we can return true
    return true;
  }
}
