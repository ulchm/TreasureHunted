package com.norcode.bukkit.treasurehunted.commands;

import com.norcode.bukkit.metalcore.command.BaseCommand;
import com.norcode.bukkit.metalcore.command.CommandError;
import com.norcode.bukkit.treasurehunted.TreasureHunted;
import org.bukkit.command.CommandSender;

import java.util.LinkedList;

public class NewCommand extends BaseCommand {

    public NewCommand(TreasureHunted plugin) {
        super(plugin, "new", new String[] {}, "treasurehunted.command.new", null);
    }

    @Override
    protected void onExecute(CommandSender commandSender, String label, LinkedList<String> args) throws CommandError {
        TreasureHunted newPlugin = (TreasureHunted) plugin;
        newPlugin.spawnNewChest();
    }
}
