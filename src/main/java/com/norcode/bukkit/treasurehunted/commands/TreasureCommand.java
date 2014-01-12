package com.norcode.bukkit.treasurehunted.commands;

import com.norcode.bukkit.metalcore.command.CommandError;
import com.norcode.bukkit.metalcore.command.RootCommand;
import com.norcode.bukkit.treasurehunted.TreasureHunted;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedList;

public class TreasureCommand extends RootCommand {
    public TreasureCommand(TreasureHunted plugin) {
        super(plugin, "treasure", new String[] {}, "treasure.command", null);
        registerSubcommand(new StartCommand(plugin));
        registerSubcommand(new StopCommand(plugin));
        registerSubcommand(new NewCommand(plugin));
    }

    @Override
    protected void onExecute(CommandSender commandSender, String label, LinkedList<String> args) throws CommandError {
        Player p = (Player) commandSender;
        p.sendMessage("Type /treasure start to join in the treasure hunt!");
    }
}
