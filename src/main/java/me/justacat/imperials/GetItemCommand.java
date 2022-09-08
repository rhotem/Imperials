package me.justacat.imperials;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GetItemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {


        if (sender instanceof Player) {

            if (args.length == 1) {

                CustomItem item = CustomItem.getCustomItem(args[0]);

                if (item == null) return false;

                ((Player) sender).getInventory().addItem(item);

                return true;
            }


        }

        return false;

    }
}
