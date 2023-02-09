package com.github.tacowasa059.ascentbattle.Commands;

import com.github.tacowasa059.ascentbattle.AscentBattle;
import com.github.tacowasa059.ascentbattle.utils.Start;
import com.github.tacowasa059.ascentbattle.utils.Finish;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class CommandController implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player player=(Player) sender;
            if(!player.isOp()){
                player.sendMessage(ChatColor.RED+"このコマンドを実行する権限がありません。");
                return true;
            }
            if(args.length == 1) {
                //start
                if (args[0].equalsIgnoreCase(TabComplete.START)) {
                    if (!AscentBattle.getStatus()) {
                        player.sendMessage(ChatColor.GREEN + "ゲームを開始しました");
                        Start.countdown();
                        player.getWorld().setDifficulty(Difficulty.PEACEFUL);
                    } else {
                        player.sendMessage(ChatColor.RED + "ゲームは既に実行されています");
                    }
                }
                //stop
                else if (args[0].equalsIgnoreCase(TabComplete.STOP)) {
                    if (AscentBattle.getStatus()) {
                        Finish.stop("");
                        player.sendMessage(ChatColor.GREEN + "ゲームを停止しました");
                    } else {
                        player.sendMessage(ChatColor.RED + "ゲームは既に停止しています");
                    }
                }
                //reloadConfig
                else if(args[0].equals(TabComplete.CONFIG_RELOAD)){
                    AscentBattle.plugin.reloadConfig();
                    player.sendMessage(ChatColor.GREEN + "configをリロードしました");
                }

            }
            else if(args.length==2){
                //showconfig
                if(args[0].equalsIgnoreCase(TabComplete.CONFIG_SHOW)){
                    String str=args[1];
                    if(str.equalsIgnoreCase(TabComplete.CONFIG_SPAWN_LOCATION)){
                        double x=AscentBattle.plugin.getConfig().getDouble(str+".x");
                        double y=AscentBattle.plugin.getConfig().getDouble(str+".y");
                        double z=AscentBattle.plugin.getConfig().getDouble(str+".z");

                        player.sendMessage(ChatColor.AQUA+str+" : "+x+" "+y+" "+z);
                        return true;
                    }
                    player.sendMessage(ChatColor.AQUA+str+" : "+AscentBattle.plugin.getConfig().get(str));
                }
                //setconfig spawnlocation
                else if(args[0].equalsIgnoreCase(TabComplete.CONFIG_SET)){
                    //初期スポーン位置の中心を設定
                    if(args[1].equalsIgnoreCase(TabComplete.CONFIG_SPAWN_LOCATION)){
                        AscentBattle.plugin.getConfig().set(TabComplete.CONFIG_SPAWN_LOCATION+".x",player.getLocation().getX());
                        AscentBattle.plugin.getConfig().set(TabComplete.CONFIG_SPAWN_LOCATION+".y",player.getLocation().getY());
                        AscentBattle.plugin.getConfig().set(TabComplete.CONFIG_SPAWN_LOCATION+".z",player.getLocation().getZ());
                        player.sendMessage(ChatColor.GREEN + "スポーン位置を"+player.getLocation().getX()+" "+player.getLocation().getY()+" "+player.getLocation().getZ()+"に設定しました。");
                        AscentBattle.plugin.saveConfig();
                    }
                }
            }
            //setconfig
            else if(args.length==3){
                if(args[0].equalsIgnoreCase(TabComplete.CONFIG_SET)){
                    String str=args[1];
                    String value=args[2];
                    if(Arrays.asList(TabComplete.CONFIG_LIST).contains(str)){
                        if(str==TabComplete.CONFIG_SPAWN_LOCATION){
                            player.sendMessage(ChatColor.RED+"座標の指定はx y zの3つを指定してください");
                            return true;
                        }
                        try{
                            AscentBattle.plugin.getConfig().set(str,Double.parseDouble(value));
                            AscentBattle.plugin.saveConfig();
                            player.sendMessage(ChatColor.GREEN+str+"は"+ChatColor.AQUA+value+ChatColor.GREEN+"に設定されました");
                        }catch (NumberFormatException e){
                            player.sendMessage(ChatColor.RED+"値は数値で指定してください");
                            return true;
                        }
                    }
                    else{
                        player.sendMessage(ChatColor.RED+"この引数はコンフィグで設定されていません。");
                    }
                }
            }
            //set config spawnlocation x y z
            else if(args.length==5){
                if(args[0].equalsIgnoreCase(TabComplete.CONFIG_SET)){
                    if(args[1].equalsIgnoreCase(TabComplete.CONFIG_SPAWN_LOCATION)) {
                        double value_x,value_y,value_z;
                        try{
                            value_x=Double.parseDouble(args[2]);
                            value_y=Double.parseDouble(args[3]);
                            value_z=Double.parseDouble(args[4]);
                        }
                        catch(NumberFormatException e){
                            player.sendMessage(ChatColor.RED+"座標は数値で指定してください");
                            return true;
                        }
                        AscentBattle.plugin.getConfig().set(TabComplete.CONFIG_SPAWN_LOCATION+".x",value_x);
                        AscentBattle.plugin.getConfig().set(TabComplete.CONFIG_SPAWN_LOCATION+".y",value_y);
                        AscentBattle.plugin.getConfig().set(TabComplete.CONFIG_SPAWN_LOCATION+".z",value_z);
                        player.sendMessage(ChatColor.GREEN + "スポーン位置を"+value_x+" "+value_y+" "+value_z+"に設定しました。");
                        AscentBattle.plugin.saveConfig();
                    }
                    else{
                        player.sendMessage(ChatColor.RED+"この引数はコンフィグで設定されていません。");
                    }
                }
            }
            else{
                player.sendMessage(ChatColor.RED + "コマンドの引数が不適切です");
                //player.sendMessage(ChatColor.RED + "/ascentbattle start/stop");
            }
        }
        return true;
    }
}
