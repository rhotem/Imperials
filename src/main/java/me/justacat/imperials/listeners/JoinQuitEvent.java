package me.justacat.imperials.listeners;

import me.justacat.imperials.CustomItem;
import me.justacat.imperials.Imperials;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoinQuitEvent implements Listener {



    @EventHandler
    public void onJoin(PlayerJoinEvent e) {




        new BukkitRunnable() {
            final Player player = e.getPlayer();
            @Override
            public void run() {

                if (!player.isOnline()) {
                    this.cancel();
                    return;
                }

                Inventory inventory = player.getInventory();


                for (ItemStack itemStack : inventory.getContents()) {

                    CustomItem item = CustomItem.getCustomItem(itemStack);

                    if (item != null) {

                        item.whileInInventory(player);

                    }


                }


                List<ItemStack> itemStackList = new ArrayList<>(Arrays.asList(player.getEquipment().getArmorContents()));

                itemStackList.add(player.getInventory().getItemInMainHand());
                itemStackList.add(player.getInventory().getItemInOffHand());

                for (ItemStack itemStack : itemStackList) {

                    CustomItem item = CustomItem.getCustomItem(itemStack);

                    if (item != null) {

                        item.whileOnUse(player);

                    }


                }





            }

        }.runTaskTimer(JavaPlugin.getPlugin(Imperials.class), 10, 10);

    }


}
