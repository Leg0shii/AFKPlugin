package de.legoshi.afkplugin_16.listener;

import de.legoshi.afkplugin_16.database.DBManager;
import de.legoshi.afkplugin_16.util.PlayerManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class QuitListener implements Listener {

    private final PlayerManager playerManager;
    private final DBManager dbManager;

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (playerManager.getPlayerAFKMap().containsKey(player)) {
            dbManager.addAFKEntry(player, System.currentTimeMillis() - playerManager.getPlayerObjectHashMap().get(player).getLastMove());
            playerManager.getPlayerAFKMap().remove(player);
        }
        playerManager.getPlayerObjectHashMap().remove(player);

    }


}
