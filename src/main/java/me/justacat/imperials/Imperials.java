package me.justacat.imperials;

import me.justacat.imperials.commands.GetItemCommand;
import me.justacat.imperials.commands.GetItemTabComplete;
import me.justacat.imperials.listeners.ClickEvent;
import me.justacat.imperials.listeners.JoinQuitEvent;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class Imperials extends JavaPlugin {

    @Override
    public void onEnable() {

        CustomItem.customItemKey = new NamespacedKey(this, "CustomItem");
        CustomItem.uuidKey = new NamespacedKey(this, "DrillUUID");

        getCommand("GetItem").setExecutor(new GetItemCommand());
        getCommand("GetItem").setTabCompleter(new GetItemTabComplete());

        Bukkit.getPluginManager().registerEvents(new ClickEvent(), this);
        Bukkit.getPluginManager().registerEvents(new JoinQuitEvent(), this);



    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
