package com.github.tacowasa059.ascentbattle.utils;

import com.github.tacowasa059.ascentbattle.AscentBattle;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
//タイマー・時間計測
public class Timer {
    private static BukkitTask task;
    private static int second;

    public static void start() {
        second=0;
        task = new BukkitRunnable() {
            @Override
            public void run() {
                second += 1;
                if (second == AscentBattle.plugin.getConfig().getInt("TimeLimit")||!AscentBattle.getStatus()) {
                    task.cancel();
                    Finish.stop("");
                    return;
                }
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if(second<60)player.sendActionBar(Bukkit.getOnlinePlayers().size()+"人が参加中 "+"経過時間" + second + "秒");
                    else player.sendActionBar(Bukkit.getOnlinePlayers().size()+"人が参加中 "+"経過時間"+(second/60)+"分"+second%60+"秒");
                }
            }
        }.runTaskTimer(AscentBattle.plugin, 0, 20);
    }
}
