package me.justacat.imperials.listeners;

import me.justacat.imperials.CustomItem;
import me.justacat.imperials.Imperials;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;

public class ClickEvent implements Listener {


    @EventHandler
    public void onClick(PlayerInteractEvent e) {

        if (e.getHand() == EquipmentSlot.HAND) {


            CustomItem item = CustomItem.getCustomItem(e.getItem());

            if (item == null) return;

            if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {

                item.onLeftClick(e);


            } else if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

                item.onRightClick(e);

            }


        }

    }

    @EventHandler
    public void bowShotEvent(EntityShootBowEvent e) {

        CustomItem item = CustomItem.getCustomItem(e.getBow());

        if (item == null) return;

        particles(e.getProjectile());


    }

    public void particles(Entity entity) {

        if (entity.isDead()) return;

        if (entity instanceof Arrow arrow) {

            if (arrow.isOnGround()) return;

            Location location = arrow.getLocation();

            Bukkit.getScheduler().runTaskLater(JavaPlugin.getPlugin(Imperials.class), () -> {

                location.getWorld().spawnParticle(Particle.NOTE, location, 1, 0, 0, 0, 0);
                location.getWorld().playSound(location, Sound.BLOCK_NOTE_BLOCK_PLING, 0.7F, 0.7F);
                particles(entity);

            }, 1);


        }







    }



}
