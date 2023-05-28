package com.beyondstranded;

import com.google.gson.JsonObject;

import java.util.Random;

public class Combat {
    private Player player;
    private NPC hunter;

    public Combat(Player player, NPC hunter) {
        this.player = player;
        this.hunter = hunter;
    }

    public void startCombat() {
        if (player.getLocation().getName().equals("Cave")) {
            int damage = player.getHealth() / 2;
            player.decreaseHealth(damage);

            System.out.println("Hunter attacks you! RUN!!!");
            System.out.println("Player's remaining health: " + player.getHealth());
        }
    }
}