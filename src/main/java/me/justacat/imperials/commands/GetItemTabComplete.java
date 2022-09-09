package me.justacat.imperials.commands;

import me.justacat.imperials.RegisteredItems;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GetItemTabComplete implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        List<String> list = new ArrayList<>();

        if (args.length == 1) {
            for (RegisteredItems item : RegisteredItems.values()) {

                list.add(item.name());

            }
            return StringUtil.copyPartialMatches(args[0], list, new ArrayList<>());

        }

        return list;

    }
}
