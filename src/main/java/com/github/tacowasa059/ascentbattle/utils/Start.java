package com.github.tacowasa059.ascentbattle.utils;

import com.github.tacowasa059.ascentbattle.AscentBattle;
import com.github.tacowasa059.ascentbattle.Listeners.SpawnProcess;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
//ゲーム開始用
public class Start {
    private static BukkitTask task;
    private static int count;

    public static void countdown() {
        count = 10;
        task = new BukkitRunnable() {
            @Override
            public void run() {
                count = count > 0 ? count - 1 : 0;
                if (count == 0) {
                    start();
                    Timer.start();
                    task.cancel();
                    return;
                }
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendActionBar("あと" + count + "秒で開始します。");
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 1);
                }
            }
        }.runTaskTimer(AscentBattle.plugin, 0, 20);
    }

    public static void start() {
        //ゲームの開始処理
        AscentBattle.setStatus(true);

        AscentBattle.plugin.rankProcess.init();//scoreboardの初期化
        AscentBattle.plugin.killCount.init();
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitle(ChatColor.RED +""+ChatColor.BOLD+"試合開始", "", 1, 20, 10);
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            player.setFoodLevel(20);
            player.getInventory().clear();

            ItemStack sword = new ItemStack(Material.STONE_SWORD);
            ItemMeta sword_meta = sword.getItemMeta();
            sword_meta.setUnbreakable(true);
            sword.setItemMeta(sword_meta);

            player.getInventory().addItem(sword);
            player.setGameMode(GameMode.ADVENTURE);
            SpawnProcess.Spawn(player);
        }
    }
}
