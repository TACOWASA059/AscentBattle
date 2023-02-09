package com.github.tacowasa059.ascentbattle.Commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TabComplete implements TabCompleter  {
    public static final String MAIN="ascentbattle";
    public static final String START="start";
    public static final String STOP="stop";

    public static final String CONFIG_SET = "setConfig";
    public static final String CONFIG_RELOAD = "reloadConfig";
    public static final String CONFIG_SHOW = "showConfig";
    public static final String CONFIG_SPAWN_LOCATION="SpawnLocation";
    public static final String[] CONFIG_LIST={"Max-Layer",CONFIG_SPAWN_LOCATION,"y_gain","R_0","dR","TimeLimit","Gamemode"};

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if(command.getName().equals(MAIN)) {
            if (args.length == 1) {
                String input = args[args.length - 1];
                String[] target = {START, STOP, CONFIG_SHOW, CONFIG_RELOAD,CONFIG_SET};
                completions.addAll(Arrays.asList(target).stream()
                        .filter(e -> e.startsWith(input)).collect(Collectors.toList()));
            } else if (args.length == 2 && (args[0].equals(CONFIG_SET)||args[0].equals(CONFIG_SHOW))) {
                String input = args[args.length - 1];
                completions.addAll(Arrays.asList(CONFIG_LIST).stream()
                        .filter(e -> e.startsWith(input)).collect(Collectors.toList()));
            }
        }
        return completions;
    }
}