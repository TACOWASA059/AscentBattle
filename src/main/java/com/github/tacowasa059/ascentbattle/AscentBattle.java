package com.github.tacowasa059.ascentbattle;

import com.github.tacowasa059.ascentbattle.Commands.CommandController;
import com.github.tacowasa059.ascentbattle.Commands.TabComplete;
import com.github.tacowasa059.ascentbattle.Listeners.PlayerKillDeathEvent;
import com.github.tacowasa059.ascentbattle.ScoreboardProcess.KillCount;
import com.github.tacowasa059.ascentbattle.ScoreboardProcess.RankProcess;
import org.bukkit.plugin.java.JavaPlugin;

public final class AscentBattle extends JavaPlugin {
    public static AscentBattle plugin;
    private static boolean status=false;
    public static RankProcess rankProcess;
    public static KillCount killCount;
    @Override
    public void onEnable() {
        plugin=this;
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        rankProcess =new RankProcess();
        killCount = new KillCount();
        getCommand(TabComplete.MAIN).setExecutor(new CommandController());
        getCommand(TabComplete.MAIN).setTabCompleter(new TabComplete());
        getServer().getPluginManager().registerEvents(new PlayerKillDeathEvent(rankProcess,killCount),this);
    }
    @Override
    public void onDisable() {}
    public static void setStatus(boolean flag){
        status=flag;
    }
    public static boolean getStatus(){
        return status;
    }
}
