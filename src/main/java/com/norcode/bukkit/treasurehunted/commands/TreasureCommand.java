package com.norcode.bukkit.treasurehunted.commands;

import com.norcode.bukkit.metalcore.command.BaseCommand;
import com.norcode.bukkit.metalcore.command.CommandError;
import com.norcode.bukkit.treasurehunted.TreasureHunted;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedList;

public class TreasureCommand extends BaseCommand {
    public TreasureCommand(TreasureHunted plugin) {
        super(plugin, "treasure", new String[] {}, "treasure.command", null);
        registerSubcommand(new StartCommand(plugin));
        registerSubcommand(new StopCommand(plugin));
    }

    @Override
    protected void onExecute(CommandSender commandSender, String label, LinkedList<String> args) throws CommandError {
        Player p = (Player) commandSender;
        p.sendMessage("Treasure Command");
    }
}
