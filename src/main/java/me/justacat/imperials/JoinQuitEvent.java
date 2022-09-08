package me.justacat.imperials;

import me.justacat.customitems.CustomItems;
import me.justacat.customitems.items.InfinityItem;
import me.justacat.customitems.items.drills.WoodenDrill;
import me.justacat.customitems.items.misc.Compactor;
import me.justacat.customitems.items.misc.FuelGenerator;
import me.justacat.customitems.misc.FuelManager;
import me.justacat.customitems.misc.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoinQuitEvent implements Listener {



    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        FuelManager.loadPlayer(e.getPlayer());
        ScoreboardManager.showScoreboard(e.getPlayer());

        if (!e.getPlayer().hasPlayedBefore()) {
            Bukkit.broadcast(Chat.colorMessage("&7" + e.getPlayer().getName() + " &ais new to the server! Say welcome!"), "");
            e.getPlayer().getInventory().addItem(new WoodenDrill(1));
            e.getPlayer().getInventory().addItem(new Compactor(1));
            FuelManager.fuelMap.put(e.getPlayer().getUniqueId(), 300);
        }



        new BukkitRunnable() {
            final Player player = e.getPlayer();
            @Override
            public void run() {

                if (!player.isOnline()) {
                    this.cancel();
                    return;
                }

                Inventory inventory = player.getInventory();

                FuelGenerator.fuelGeneratorCounter.put(player.getUniqueId(), false);

                for (ItemStack itemStack : inventory.getContents()) {

                    CustomItem item = CustomItem.getCustomItem(itemStack);

                    if (item != null) {

                        item.whileInInventory(player);

                    }

                    InfinityItem infinityItem = InfinityItem.fromItem(itemStack);

                    if (infinityItem != null) {
                        infinityItem.whileInInventory(player);
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

                    InfinityItem infinityItem = InfinityItem.fromItem(itemStack);

                    if (infinityItem != null) {
                        infinityItem.whileOnUse(player);
                    }

                }





            }

        }.runTaskTimer(CustomItems.instance, 10, 10);

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {

        FuelManager.savePlayer(e.getPlayer());
        FuelManager.fuelMap.remove(e.getPlayer().getUniqueId());

        try {

            AttributeModifier modifier = null;

            for (AttributeModifier am : e.getPlayer().getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getModifiers()) {

                if (am.getName().equals("GoldenTalisman")) {
                    modifier = am;
                }

            }

            e.getPlayer().getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).removeModifier(modifier);
        } catch (Exception ignored) {

        }



    }


}
