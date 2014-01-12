package com.norcode.bukkit.treasurehunted;

import com.norcode.bukkit.metalcore.MetalCorePlugin;
import com.norcode.bukkit.metalcore.util.ConfigAccessor;
import com.norcode.bukkit.metalcore.util.ParseUtil;
import com.norcode.bukkit.treasurehunted.commands.TreasureCommand;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
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
        getServer().getPluginManager().registerEvents(new TreasureListener(this), this);
        new TreasureCommand(this);
        if (getTreasureLocation() != null) {
            debug("Treasure location loaded.");
        } else {
            spawnNewChest();
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public void spawnNewChest() {
        Location loc = getRandomLocation();
        while (!checkLocation(loc)) {
            loc = getRandomLocation();
        }
        loc.getBlock().setType(Material.CHEST);
        treasureConfig.getConfig().set("location", ParseUtil.locationToString(loc, false));
        treasureConfig.saveConfig();
        Chest chest = (Chest) loc.getBlock().getState();
        chest.getInventory().addItem(getPrizeStack());
        getServer().broadcastMessage("A new treasure has been spawned!  Type /treasure start and use your compass to join the hunt!");
    }

    private Location getRandomLocation() {
        this.debug("Generating random location for treasure");
        List<String> worlds = this.getConfig().getStringList("enabled-worlds");
        String worldName = worlds.get(rand.nextInt(worlds.size()));
        Integer x = rand.nextInt(this.getConfig().getInt("max-distance"));
        Integer z = rand.nextInt(this.getConfig().getInt("max-distance"));
        Integer y = this.getServer().getWorld(worldName).getHighestBlockYAt(x, z);
        Location loc = new Location(this.getServer().getWorld(worldName),x,y,z);
        return loc;
    }

    private Boolean checkLocation(Location location){
        if (this.getServer().getWorld(location.getWorld().getName()).getHighestBlockAt(location.getBlockX(), location.getBlockZ()).getType().isSolid() == false) {
            this.debug("Non solid block found, can't spawn treasure here");
            return false;
        }
        for (Player p: this.getServer().getOnlinePlayers()) {
            if (location.distance(p.getLocation()) < this.getConfig().getInt("min-player-distance")) {
                debug("Player too close, can't spawn treasure here");
                return false;
            }
        }
        return true;
    }

    private ItemStack getPrizeStack() {
        /* TO-DO: Add More Prizes */
        return new ItemStack(Material.DIAMOND, 16);
    }

    public Location getTreasureLocation() {
        try {
            return (Location) ParseUtil.parseLocation(treasureConfig.getConfig().getString("location"));
        } catch (NullPointerException e) {
            return null;
        }
    }

    public void updateCompass(Player p) {
        p.setCompassTarget(getTreasureLocation());
    }

}
