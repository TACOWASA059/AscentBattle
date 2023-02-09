package com.github.tacowasa059.ascentbattle.utils;

import com.github.tacowasa059.ascentbattle.AscentBattle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
//ゲーム終了用
public class Finish {
    public static void stop(String str){
        //ゲームの終了処理
        if(!AscentBattle.getStatus())return;
        AscentBattle.setStatus(false);
        Bukkit.getScheduler().runTaskLater(AscentBattle.plugin, () -> {
            for(Player player1 : Bukkit.getOnlinePlayers()){
                player1.sendTitle(ChatColor.RED +""+ChatColor.BOLD+ "試合終了",str,1,60,1);
                player1.playSound(player1.getLocation(), Sound.BLOCK_ANVIL_USE,1,1);
            }
        }, 3L);
    }
}
