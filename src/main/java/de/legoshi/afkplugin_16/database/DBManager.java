package de.legoshi.afkplugin_16.database;

import de.legoshi.afkplugin_16.Main;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;

@Getter
public class DBManager {

    private AsyncMySQL mySQL;

    public AsyncMySQL initializeTables() {

        mySQL = connectToDB();
        if(mySQL != null) {
            Bukkit.getConsoleSender().sendMessage("Database Connected");
            //create table for playerdata
            mySQL.update("CREATE TABLE IF NOT EXISTS playerdata (afkid INT AUTO_INCREMENT, playeruuid VARCHAR(255)" +
                ", playername VARCHAR(255), afktime BIGINT, day INT, month INT, year INT, PRIMARY KEY(afkid));");
        }
        return mySQL;
    }

    public AsyncMySQL connectToDB() {

        Main instance = Main.getInstance();
        try {
            //mySQL = new AsyncMySQL(instance, "localhost", 3306, "root", "root", "afkdb");
            mySQL = new AsyncMySQL(instance, instance.host, instance.port, instance.user, instance.password, instance.database);
            return mySQL;
        } catch (SQLException | ClassNotFoundException throwables) { throwables.printStackTrace(); }
        return null;
    }

    public void addAFKEntry(Player player, long afkTime) {
        String playername = player.getName();
        String uuid = player.getUniqueId().toString();

        LocalDate currentdate = LocalDate.now();

        int day = currentdate.getDayOfMonth();
        int month = currentdate.getMonth().getValue();
        int year = currentdate.getYear();

        mySQL.update("INSERT INTO playerdata (playeruuid, playername, afktime, day, month, year) " +
            "VALUES ('" + uuid + "','" + playername + "'," + afkTime + "," + day + "," + month + "," + year + ");");
    }

    public void deleteAFKEntry(String playername) {
        String uuid = Main.getInstance().playerManager.fetchOffline(playername);
        mySQL.update("DELETE FROM playerdata WHERE playeruuid = '" + uuid + "';");
    }

    public void deleteALL() {
        mySQL.update("DELETE FROM playerdata;)");
    }

}
