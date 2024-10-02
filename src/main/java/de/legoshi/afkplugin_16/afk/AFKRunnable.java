package de.legoshi.afkplugin_16.afk;

import de.legoshi.afkplugin_16.Main;
import de.legoshi.afkplugin_16.util.PlayerManager;
import de.legoshi.afkplugin_16.util.PlayerObject;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class AFKRunnable {

    private final PlayerManager playerManager;
    private final Main instance;

    public void run() {

        for(Player player : playerManager.getPlayerObjectHashMap().keySet()) {

            if(!player.hasPermission("mania.afk.bypass")) {
                PlayerObject playerObject = playerManager.getPlayerObjectHashMap().get(player);
                if (playerObject.compareLocations()) {
                    //player.sendMessage("not moving since: " + (System.currentTimeMillis() - playerObject.getLastMove())/1000 + "s");
                    if((System.currentTimeMillis() - playerObject.getLastMove()) >= instance.secondsTillAFK) {
                        if (!playerObject.isAfk()) {
                            playerManager.getPlayerAFKMap().put(player, playerObject);
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', instance.afkMessage));
                            playerObject.setAfk(true);
                            playerObject.setLocation(player.getLocation());
                            return;
                        }
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', instance.afkSinceMessage.replace("{min}",
                            (System.currentTimeMillis() - playerObject.getLastMove()) / 60000 + "")));
                    }
                    if ((System.currentTimeMillis() - playerObject.getLastMove()) > Main.getInstance().kickValue) {
                        Bukkit.broadcastMessage(
                            ChatColor.translateAlternateColorCodes('&',
                                instance.kickMessage.replace("{playername}", player.getName())));
                        player.kickPlayer("§4Du warst zu lange AFK!");
                        return;
                    }
                }
                playerObject.setLocation(player.getLocation());
            }

        }

    }

}
