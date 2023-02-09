package com.github.tacowasa059.ascentbattle.ScoreboardProcess;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

public class RankProcess {//ランク
    private static Objective objective;
    //コンストラクタ宣言
    public RankProcess(){
        this.init();
    }
    //スコアボードの初期化
    public void init(){
        org.bukkit.scoreboard.Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        this.objective = scoreboard.getObjective("AscentBattleRank");
        //既に存在しているときは作成しない
        if (objective == null) objective = scoreboard.registerNewObjective("AscentBattleRank", "dummy","ランク");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        //playerの登録と初期化
        for(Player player :Bukkit.getOnlinePlayers()){
            player.setScoreboard(scoreboard);
            Score score=objective.getScore(player.getName());
            if(score!=null)score.setScore(1);
        }
    }
    //スコアボードの値を更新するsetter
    public void setScore(Player player,int rank){
        Score score=this.objective.getScore(player.getName());
        score.setScore(rank);
    }
    //スコアボードのgetter
    public int getScore(Player player){
        Score score=objective.getScore(player.getName());
        return score.getScore();
    }
    //スコアボードの値を+1
    public void ScorePlusOne(Player player){
        Score score=this.objective.getScore(player.getName());
        setScore(player,score.getScore()+1);
    }
    //スコアボードの値を-1
    public void ScoreMinusOne(Player player){
        Score score=this.objective.getScore(player.getName());
        setScore(player,score.getScore()-1);
    }

}
