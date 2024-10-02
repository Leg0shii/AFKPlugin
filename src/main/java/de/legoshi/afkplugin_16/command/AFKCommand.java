package de.legoshi.afkplugin_16.command;

import de.legoshi.afkplugin_16.database.AsyncMySQL;
import de.legoshi.afkplugin_16.database.DBManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.time.LocalDate;

@RequiredArgsConstructor
public class AFKCommand implements CommandExecutor {

    private final DBManager dbManager;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(!(commandSender instanceof Player)) return false;
        Player player = ((Player) commandSender).getPlayer();

        if(player == null) {
            commandSender.sendMessage("§cPlayer Null???? LOL");
            return false;
        }

        if(strings.length == 0) {
            player.sendMessage("§cSyntax for AFKCommands:");
            if(player.hasPermission("mania.afk.stats.others")) player.sendMessage("§c/afk stats <name>");
            if(player.hasPermission("mania.afk.stats")) player.sendMessage("§c/afk stats");
            if(player.hasPermission("mania.afk.stats.reset")) player.sendMessage("§c/afk reset <name>");
            if(player.hasPermission("mania.afk.stats.reset.admin")) player.sendMessage("§c/afk resetglobal");
            return false;
        }

        if(strings[0].equals("stats")) {
            if(strings.length == 1) {
                String playerName = player.getName();
                showPlayerStats(player, playerName);
                return false;
            }
            String playerName = strings[1];
            showPlayerStats(player, playerName);
            return false;
        }

        if(strings[0].equals("reset") && strings.length == 2) {
            dbManager.deleteAFKEntry(strings[1]);
            player.sendMessage("§AFK Stats von " + strings[1] + " gelöscht!");
            return false;
        }

        if(strings[0].equals("resetglobal")) {
            dbManager.deleteALL();
            player.sendMessage("§cAlle AFK Stats gelöscht!");
            return false;
        }

        return false;
    }

    private void showPlayerStats(Player player, String playerName) {

        AsyncMySQL mySQL = dbManager.getMySQL();

        LocalDate currentDate = LocalDate.now();
        int day = currentDate.getDayOfMonth();
        int month = currentDate.getMonth().getValue();
        int year = currentDate.getYear();

        mySQL.query("SELECT afktime FROM playerdata WHERE playername = '" + playerName + "' " +
            "AND day = " + day + " AND month = " + month + " AND year = " + year + ";", resultSet -> {
            try {
                if(resultSet.next()) {
                    long totalTime = 0;
                    do {
                        totalTime = totalTime + resultSet.getInt("afktime");
                    } while(resultSet.next());
                    long minutes = (totalTime/60000L);
                    player.sendMessage("§a" + playerName + " war heute " + minutes + " Minuten AFK!");
                } else {
                    player.sendMessage("§a " + playerName + " war heute noch nicht AFK");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

}
