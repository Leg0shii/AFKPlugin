package de.legoshi.afkplugin_16.listener;

import de.legoshi.afkplugin_16.Main;
import de.legoshi.afkplugin_16.database.DBManager;
import de.legoshi.afkplugin_16.util.PlayerManager;
import de.legoshi.afkplugin_16.util.PlayerObject;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

@RequiredArgsConstructor
public class PlayerMoveListener implements Listener {

    private final PlayerManager playerManager;
    private final DBManager dbManager;
    private final Main instance;

    @EventHandler
    public void onMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();
        PlayerObject playerObject = playerManager.getPlayerObjectHashMap().get(player);
        if(playerManager.getPlayerAFKMap().containsKey(player)) {

            if(!playerObject.compareLocations()) {
                playerObject.setLastMove(System.currentTimeMillis());
                playerManager.getPlayerAFKMap().remove(player);
                dbManager.addAFKEntry(player, System.currentTimeMillis() - playerObject.getLastMove());
                playerObject.setAfk(false);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', instance.afkMessage));
            }

        }

        if(!playerObject.compareLocations()) {
            playerObject.setLastMove(System.currentTimeMillis());
        }

    }

}
