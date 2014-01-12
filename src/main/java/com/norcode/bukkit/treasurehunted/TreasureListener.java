package com.norcode.bukkit.treasurehunted;

import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.InventoryHolder;

public class TreasureListener implements Listener {
    private final TreasureHunted plugin;

    public TreasureListener(TreasureHunted plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ConfigurationSection cfg = plugin.getPlayerData(player);
        if (cfg.getBoolean("treasure-hunting")) {
            player.setCompassTarget(plugin.getTreasureLocation());
            player.sendMessage("You are still on the treasure hunt. Your compass direction has been updated");
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryOpen(InventoryOpenEvent event) {
        plugin.debug("Chest opened");
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof Chest){
            plugin.debug("Holder is chest");
            Chest chest = (Chest) holder;
            plugin.debug("Chest @ " + chest.getLocation().toString() + " and " + plugin.getTreasureLocation().toString());
            if (chest.getLocation().equals(plugin.getTreasureLocation())) {
                plugin.debug("Treasure has been found");
                plugin.getServer().broadcastMessage("Treasure has been found by " + event.getPlayer().getName());
                plugin.spawnNewChest();
            }
        }
    }
}
