package me.justacat.imperials;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;

import java.util.List;

public class ImperialOfSound extends CustomItem {
    public ImperialOfSound() {
        super(Material.BOW, "ImperialOfSound");
    }

    @Override
    public void onRightClick(PlayerInteractEvent e) {

    }

    @Override
    public void onLeftClick(PlayerInteractEvent e) {

        Vector vector = new Vector(1, 0, 0);
        Location location = e.getPlayer().getLocation();


        for (int i = 0; i < 45; i++) {

            vector.rotateAroundY(Math.toRadians(8));
            location.getWorld().spawnParticle(Particle.NOTE, location.clone().add(vector), 1, 0, 1, 0, 0);
            location.getWorld().playSound(location.clone().add(vector), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 0.7F);

        }

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
}
