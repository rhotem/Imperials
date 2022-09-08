package me.justacat.imperials;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class Chat {

    public static HashMap<UUID, String> playerChatRequests = new HashMap<>();
    public static HashMap<UUID, String> playerAndResult = new HashMap<>();

    public static String colorMessage(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> colorList(List<String> list) {

        if (list == null) {return null;}

        List<String> newList = new ArrayList<>();

        for (String str : list) {

            newList.add(colorMessage(str));

        }

        return newList;

    }

}
