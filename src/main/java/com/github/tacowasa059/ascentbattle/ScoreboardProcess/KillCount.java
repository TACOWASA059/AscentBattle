package com.github.tacowasa059.ascentbattle.ScoreboardProcess;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

public class KillCount {//キル数
    private Objective objective;
    //コンストラクタ宣言
    public KillCount(){
        this.init();
    }
    //スコアボードの初期化
    public void init(){
        org.bukkit.scoreboard.Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        this.objective = scoreboard.getObjective("AB-Kills");
        //既に存在しているときは作成しない
        if (objective == null) objective = scoreboard.registerNewObjective("AB-Kills", "dummy","キル数");
        objective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        //playerの登録と初期化
        for(Player player :Bukkit.getOnlinePlayers()){
            player.setScoreboard(scoreboard);
            Score score=objective.getScore(player.getName());
            if(score!=null)score.setScore(0);
        }
    }
    //スコアボードの値を更新するsetter
    public void setScore(Player player,int rank){
        Score score=this.objective.getScore(player.getName());
        score.setScore(rank);
    }
    //スコアボードの値を+1
    public void ScorePlusOne(Player player){
        Score score=this.objective.getScore(player.getName());
        setScore(player,score.getScore()+1);
    }

}
