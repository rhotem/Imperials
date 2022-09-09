package me.justacat.imperials;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ImperialOfSound extends CustomItem {

    public static Cache<UUID, Long> cooldown;

    public ImperialOfSound() {
        super(Material.BOW, "ImperialOfSound");
        cooldown = CacheBuilder.newBuilder().expireAfterWrite(30, TimeUnit.SECONDS).build();
    }

    @Override
    public void onRightClick(PlayerInteractEvent e) {

    }

    @Override
    public void onLeftClick(PlayerInteractEvent e) {

        Location location = e.getPlayer().getLocation();

        if (isInCooldown(e.getPlayer())) {
            e.getPlayer().sendMessage(Chat.colorMessage("&cYou have to wait " + getPlayerCooldown(e.getPlayer()) + " more seconds before you can do that again!"));
            return;
        }

        if (!e.getPlayer().isOp()) {
            putInCooldown(e.getPlayer());
        }



        new BukkitRunnable() {

            int times = 0;
            @Override
            public void run() {

                if (times > 12) {
                    this.cancel();
                    return;
                }

                Vector vector = new Vector(8, 0, 0);

                for (int i = 0; i < 90; i++) {

                    vector.rotateAroundY(Math.toRadians(4));
                    location.getWorld().spawnParticle(Particle.NOTE, location.clone().add(vector), 1, 0, 0, 0, 0);


                }

                location.getWorld().spawnParticle(Particle.NOTE, location, 200, 7, 0, 7, 0);

                location.getWorld().playSound(location.clone().add(vector), Sound.BLOCK_NOTE_BLOCK_PLING, 0.7F, (float) ((float) times / 45.0));
                Collection<LivingEntity> collection = location.getNearbyLivingEntities(8, 8, 8);
                collection.remove(e.getPlayer());

                collection.forEach((entity -> {
                    entity.damage(5, e.getPlayer());
                }));

                times++;



            }


        }.runTaskTimer(JavaPlugin.getPlugin(Imperials.class), 0, 5);



    }


    @Override
    public void whileOnUse(Player player) {

    }

    @Override
    public void whileInInventory(Player player) {

    }

    @Override
    public void onMine(BlockBreakEvent e) {

    }

    @Override
    public String getName() {
        return "&bImperial of Sound";
    }

    @Override
    public List<String> getCustomLore() {
        return null;
    }

    @Override
    public EquipmentSlot[] getActiveSlots() {
        return new EquipmentSlot[0];
    }


    public long getPlayerCooldown(Player player) {
        return TimeUnit.MILLISECONDS.toSeconds(cooldown.asMap().get(player.getUniqueId()) - System.currentTimeMillis());
    }

    public boolean isInCooldown(Player player) {
        return cooldown.asMap().containsKey(player.getUniqueId());
    }

    public void putInCooldown(Player player) {
        cooldown.put(player.getUniqueId(), System.currentTimeMillis() + 30000);
    }
}
