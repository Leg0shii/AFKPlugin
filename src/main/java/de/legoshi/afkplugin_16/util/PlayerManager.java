package de.legoshi.afkplugin_16.util;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;

@Getter
public class PlayerManager {

    public HashMap<Player, PlayerObject> playerObjectHashMap;
    public HashMap<Player, PlayerObject> playerAFKMap;

    public PlayerManager() {

        playerObjectHashMap = new HashMap<>();
        playerAFKMap = new HashMap<>();

    }

    public String fetchOffline(String name) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(name);
        return player.getUniqueId().toString();
    }

}
