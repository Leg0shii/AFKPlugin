package de.legoshi.afkplugin_16.listener;

import de.legoshi.afkplugin_16.util.PlayerManager;
import de.legoshi.afkplugin_16.util.PlayerObject;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@RequiredArgsConstructor
public class JoinListener implements Listener {

    private final PlayerManager playerManager;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
       playerManager.getPlayerObjectHashMap().put(player,
           new PlayerObject(player, player.getLocation(), System.currentTimeMillis(), 0, false, 0));
       playerManager.getPlayerObjectHashMap().get(player).setLocation(player.getLocation());

    }

}
