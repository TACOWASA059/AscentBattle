package com.github.tacowasa059.ascentbattle.Listeners;

import com.github.tacowasa059.ascentbattle.AscentBattle;
import com.github.tacowasa059.ascentbattle.ScoreboardProcess.KillCount;
import com.github.tacowasa059.ascentbattle.ScoreboardProcess.RankProcess;
import com.github.tacowasa059.ascentbattle.utils.Finish;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerKillDeathEvent implements Listener {
    public final RankProcess rankProcess;
    public final KillCount killCount;

    public PlayerKillDeathEvent( RankProcess rankProcess,KillCount killCount) {
        this.rankProcess =rankProcess;
        this.killCount=killCount;
    }

    //試合中のキルイベント処理
    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent e){
        if(!AscentBattle.getStatus()) return;
        Player player=e.getEntity().getPlayer();
        Player killer=e.getEntity().getKiller();
        e.setCancelled(true);
        if(killer!=null){
            killCount.ScorePlusOne(killer);//キル数
            if(rankProcess.getScore(killer)<AscentBattle.plugin.getConfig().getInt("Max-Layer")) {
                rankProcess.ScorePlusOne(killer);//ランク
            }
            if(rankProcess.getScore(killer)==AscentBattle.plugin.getConfig().getInt("Max-Layer")){
                if(AscentBattle.plugin.getConfig().getInt("Gamemode")==1){
                    Finish.stop("勝者 "+killer.getName());
                }
                killer.sendTitle(ChatColor.RED + "You killed",ChatColor.GREEN+"最上階"+ rankProcess.getScore(killer)+"階",1,10,1);
            }
            else killer.sendTitle(ChatColor.RED + "You killed",ChatColor.GREEN+Integer.valueOf(rankProcess.getScore(killer)).toString()+"階",1,10,1);
            SpawnProcess.Spawn(killer);
            killer.playSound(killer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1,1);
        }
        if(player!=null){
            if(rankProcess.getScore(player)>1){
                rankProcess.ScoreMinusOne(player);
            }
            player.sendTitle(ChatColor.RED + "You Died",ChatColor.GREEN+Integer.valueOf(rankProcess.getScore(player)).toString()+"階",1,10,1);
            //teleportを遅らせる
            Bukkit.getScheduler().runTaskLater(AscentBattle.plugin, () -> {
                SpawnProcess.Spawn(player);
            }, 3L);
        }
    }

    //試合中以外でダメージオフ
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if(AscentBattle.getStatus()) return;
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            event.setCancelled(true);
        }
    }
}

