package com.norcode.bukkit.treasurehunted.commands;

import com.norcode.bukkit.metalcore.command.BaseCommand;
import com.norcode.bukkit.metalcore.command.CommandError;
import com.norcode.bukkit.treasurehunted.TreasureHunted;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedList;

public class StopCommand extends BaseCommand {

    public StopCommand(TreasureHunted plugin) {
        super(plugin, "stop", new String[] {}, "treasurehunted.command.stop", null);
    }

    @Override
    protected void onExecute(CommandSender commandSender, String label, LinkedList<String> args) throws CommandError {
        Player p = (Player) commandSender;
        p.sendMessage("Treasure hunt ended.");
    }
}
