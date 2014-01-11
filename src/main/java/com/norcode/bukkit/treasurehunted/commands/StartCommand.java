package com.norcode.bukkit.treasurehunted.commands;

import com.norcode.bukkit.metalcore.command.BaseCommand;
import com.norcode.bukkit.metalcore.command.CommandError;
import com.norcode.bukkit.treasurehunted.TreasureHunted;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.LinkedList;

public class StartCommand extends BaseCommand {

    public StartCommand(TreasureHunted plugin) {
        super(plugin, "start", new String[] {}, "treasurehunted.command.start", null);
    }

    @Override
    protected void onExecute(CommandSender commandSender, String label, LinkedList<String> args) throws CommandError {
        TreasureHunted newPlugin = (TreasureHunted) plugin;
        newPlugin.updateCompass((Player) commandSender);
        Player p = (Player) commandSender;
        p.sendMessage("You have started the treasure hunt.  Use a compass to find it!");
    }
}
