package me.justacat.imperials;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public abstract class CustomItem extends ItemStack {

    public static NamespacedKey customItemKey;

    public static NamespacedKey uuidKey;

    public CustomItem(Material material, String id) {
        super(material);

        ItemMeta meta = getItemMeta();

        meta.getPersistentDataContainer().set(customItemKey, PersistentDataType.STRING, id);

        setItemMeta(meta);

        ItemMeta itemMeta = getItemMeta();


        itemMeta.setLore(Chat.colorList(getLore()));

        itemMeta.setDisplayName(Chat.colorMessage(getName()));

        if (hideFlags()) {
            itemMeta.addItemFlags(ItemFlag.values());
        }

        if (hideEnchants()) {
            if (!hideFlags()) {
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        } else {
            itemMeta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        itemMeta.setUnbreakable(unbreakable());


        for (EquipmentSlot slot : getActiveSlots()) {
            itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), id, getAttackSpeed(), AttributeModifier.Operation.ADD_NUMBER, slot));

        }

        HashMap<Enchantment, Integer> enchantsMap = getEnchants();

        enchantsMap.forEach((key, value) -> itemMeta.addEnchant(key, value, true));

        setItemMeta(itemMeta);


    }

    public abstract void onRightClick(PlayerInteractEvent e);

    public abstract void onLeftClick(PlayerInteractEvent e);

    public abstract void whileOnUse(Player player);

    public abstract void whileInInventory(Player player);

    public abstract void onMine(BlockBreakEvent e);

    public abstract String getName();

    public abstract List<String> getCustomLore();

    public abstract EquipmentSlot[] getActiveSlots();

    public int getDamage() {return 1;}

    public double getAttackSpeed() {return 1.6;}

    public boolean hideEnchants() {return false;}

    public boolean unbreakable() {return true;}

    public void onAttack(EntityDamageByEntityEvent e) {}

    public List<String> getLore() {

        List<String> lore = new ArrayList<>();

        lore.add("&0");

        if (getCustomLore() != null) {

            lore.addAll(getCustomLore());
            lore.add("&0");
        }

        if (getDamage() != 1) {

            lore.add("&7Damage: " + getDamage());
            lore.add("&0");

        }

        if (lore.size() == 1) {return null;}

        return lore;


    }

    public boolean hideFlags() {return false;}

    public String getID() {return getItemMeta().getPersistentDataContainer().get(customItemKey, PersistentDataType.STRING);}


    public HashMap<Enchantment, Integer> getEnchants() {
        return new HashMap<>();
    }

    public long getGunCooldown() {return 0;}


    public void generateUUID(ItemStack item) {
        ItemMeta meta = item.getItemMeta();

        UUID uuid = UUID.randomUUID();

        meta.getPersistentDataContainer().set(uuidKey, PersistentDataType.STRING, uuid.toString());

        item.setItemMeta(meta);
    }

    public UUID getUUID(ItemStack item) {

        try {
            return UUID.fromString(item.getItemMeta().getPersistentDataContainer().get(uuidKey, PersistentDataType.STRING));
        } catch (NullPointerException | IllegalArgumentException e) {
            return null;
        }

    }





    public static boolean isCustomItem(ItemStack itemStack) {

        try {
            return itemStack.getItemMeta().getPersistentDataContainer().has(customItemKey, PersistentDataType.STRING);
        } catch (Exception e) {
            return false;
        }



    }
    public static CustomItem getCustomItem(ItemStack item) {

        if (!isCustomItem(item)) return null;

        return getCustomItem(item.getItemMeta().getPersistentDataContainer().get(customItemKey, PersistentDataType.STRING));



    }
    public static CustomItem getCustomItem(String id) {

        try {
            return RegisteredItems.valueOf(id).getItem();
        } catch (Exception e) {
            return null;
        }


    }













}
