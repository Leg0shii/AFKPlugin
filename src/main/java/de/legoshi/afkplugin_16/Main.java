package de.legoshi.afkplugin_16;

import de.legoshi.afkplugin_16.afk.AFKRunnable;
import de.legoshi.afkplugin_16.command.AFKCommand;
import de.legoshi.afkplugin_16.database.DBManager;
import de.legoshi.afkplugin_16.listener.JoinListener;
import de.legoshi.afkplugin_16.listener.PlayerMoveListener;
import de.legoshi.afkplugin_16.listener.QuitListener;
import de.legoshi.afkplugin_16.util.FW;
import de.legoshi.afkplugin_16.util.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main instance;
    public PlayerManager playerManager;
    public DBManager dbManager;
    public AFKRunnable afkRunnable;

    public int secondsTillAFK; // in ms
    public int checkIntervall; // in ms
    public int kickValue; // in ms
    public String kickMessage;
    public String afkMessage;
    public String afkSinceMessage;
    public String noAFK;

    public String host;
    public String database;
    public String password;
    public int port;
    public String user;

    @Override
    public void onEnable() {

        instance = this;
        loadValues();

        playerManager = new PlayerManager();
        dbManager = new DBManager();
        dbManager.initializeTables();

        afkRunnable = new AFKRunnable(playerManager, instance);
        Bukkit.getScheduler().runTaskTimer(instance, () -> {
            afkRunnable.run();
        },20, checkIntervall/50L);

        registerListeners();
        getCommand("afk").setExecutor(new AFKCommand(dbManager));
    }

    @Override
    public void onDisable() {

        for(Player all : Bukkit.getOnlinePlayers()) {
            if(playerManager.getPlayerAFKMap().containsKey(all)) {
                dbManager.addAFKEntry(all, (System.currentTimeMillis() - playerManager.getPlayerObjectHashMap().get(all).getLastMove()));
                playerManager.getPlayerAFKMap().remove(all);
            }
            playerManager.getPlayerObjectHashMap().remove(all);
        }

    }

    public static Main getInstance() {

        return instance;
    }

    private void loadValues() {

        FW fw = new FW("./AFKTimes", "AFKConfiguration.yaml");
        if(!fw.exist()) {
            fw.setValue("secondsTillAFK", 60);
            fw.setValue("checkIntervall", 30);
            fw.setValue("kickValue", 1800);
            fw.setValue("kickMessage", "&4{playername} war zu lange AFK");
            fw.setValue("afkMessage", "&cDu bist jetzt AFK!");
            fw.setValue("afkSinceMessage", "&cDu bist AFK seit {min} Minuten!");
            fw.setValue("noAFK", "&cDu bist nicht mehr AFK!");
            fw.setValue("host", "localhost");
            fw.setValue("port", 3306);
            fw.setValue("user", "root");
            fw.setValue("password", "root");
            fw.setValue("database", "afkdb");
            fw.save();
        }
        secondsTillAFK = fw.getInt("secondsTillAFK")*1000;
        checkIntervall = fw.getInt("checkIntervall")*1000;
        kickValue = fw.getInt("kickValue")*1000;
        kickMessage = fw.getString("kickMessage");
        afkMessage = fw.getString("afkMessage");
        afkSinceMessage = fw.getString("afkSinceMessage");
        noAFK = fw.getString("noAFK");
        host = fw.getString("host");
        port = fw.getInt("port");
        user = fw.getString("user");
        password = fw.getString("password");
        database = fw.getString("database");
    }

    private void registerListeners() {

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new JoinListener(playerManager), this);
        pm.registerEvents(new PlayerMoveListener(playerManager, dbManager, instance), this);
        pm.registerEvents(new QuitListener(playerManager, dbManager), this);

    }

}
