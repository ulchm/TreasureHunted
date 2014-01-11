package com.norcode.bukkit.treasurehunted;

import com.norcode.bukkit.metalcore.MetalCorePlugin;
import com.norcode.bukkit.metalcore.util.ConfigAccessor;
import com.norcode.bukkit.treasurehunted.commands.TreasureCommand;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class TreasureHunted extends MetalCorePlugin implements Listener {

    private ConfigAccessor treasureConfig;
    private static Random rand = new Random();

    @Override
    public void onEnable() {
        super.onEnable();
        treasureConfig = new ConfigAccessor(this, "treasure.yml");

        new TreasureCommand(this);
        spawnNewChest();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void spawnNewChest() {
        Location loc = getRandomLocation();
        while (!checkLocation(loc)) {
            loc = getRandomLocation();
        }
        loc.getBlock().setType(Material.CHEST);
        Chest chest = (Chest) loc.getBlock().getState();
        chest.getInventory().addItem(getPrizeStack());
    }

    private Location getRandomLocation() {
        //get random world
        List<String> worlds = this.getConfig().getStringList("enabled-worlds");
        String worldName = worlds.get(rand.nextInt(worlds.size()));

        //get random locations
        Integer x = rand.nextInt(this.getConfig().getInt("max-distance"));
        Integer z = rand.nextInt(this.getConfig().getInt("max-distance"));
        Integer y = this.getServer().getWorld(worldName).getHighestBlockYAt(x, z);

        Location loc = new Location(this.getServer().getWorld(worldName),x,y,z);
        treasureConfig.getConfig().set("location", loc);
        return loc;
    }

    private Boolean checkLocation(Location location){
        /* TO-DO: Check for lava, water, and near players */
        Biome biome = location.getBlock().getBiome();
        if (biome.name().toLowerCase().contains("ocean")) {
            return false;
        }

        return true;
    }

    private ItemStack getPrizeStack() {
        /* TO-DO: Add More Prizes */
        return new ItemStack(Material.DIAMOND, 64);
    }

    public Location getTreasureLocation() {
        return (Location) treasureConfig.getConfig().get("location");
    }

    public void updateCompass(Player p) {
        p.setCompassTarget(getTreasureLocation());
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ConfigurationSection cfg = this.getPlayerData(player);
        if (cfg.getBoolean("treasure-hunting")) {
            player.setCompassTarget(getTreasureLocation());
        }
    }
}
