package com.github.tacowasa059.ascentbattle.Listeners;

import com.github.tacowasa059.ascentbattle.AscentBattle;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Random;

public class SpawnProcess {
    public static void Spawn(Player player){//Spawn位置の指定とteleport
        int rank=AscentBattle.plugin.rankProcess.getScore(player);
        Double x= AscentBattle.plugin.getConfig().getDouble("SpawnLocation.x");
        Double y= AscentBattle.plugin.getConfig().getDouble("SpawnLocation.y");
        Double z= AscentBattle.plugin.getConfig().getDouble("SpawnLocation.z");
        y+=AscentBattle.plugin.getConfig().getDouble("y_gain")*(rank-1);

        Random random = new Random();
        Double R=AscentBattle.plugin.getConfig().getDouble("R_0")+AscentBattle.plugin.getConfig().getDouble("dR")*(rank-1)-0.5;
        Double r=R*Math.sqrt(random.nextDouble());
        Double theta=random.nextDouble()*2.0*Math.PI;
        if(r<0)r=0.0;
        x+=r*Math.sin(theta);
        z+=r*Math.cos(theta);
        player.teleport(new Location(player.getWorld(),x,y,z));
        player.setHealth(20.0);
    }
}
