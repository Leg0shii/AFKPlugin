package de.legoshi.afkplugin_16.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Getter
@Setter
@AllArgsConstructor
public class PlayerObject {

    private Player player;
    private Location location;

    private long lastMove;
    private long afkTime;
    private boolean afk;

    private int taskID;

    public boolean compareLocations() {
        Location l1 = this.location;
        Location l2 = player.getLocation();
        if ((l1.getX() == l2.getX() && l1.getZ() == l2.getZ())
            || (l1.getYaw() == l2.getYaw() && l1.getPitch() == l2.getPitch())) return true;
        return false;
    }

}
