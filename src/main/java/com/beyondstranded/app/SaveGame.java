package com.beyondstranded.app;

import com.beyondstranded.Inventory;
import com.beyondstranded.Item;
import com.beyondstranded.Location;
import com.beyondstranded.Player;
import com.google.gson.*;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

class SaveGame {
   private final Location location;
   private final Player player;
   private final Inventory inventory;

   public SaveGame(Location location, Player player, Inventory inventory) {
      this.location = location;
      this.player = player;
      this.inventory = inventory;
   }


//   public void serialize(String filename) throws IOException {
//      try (FileWriter writer = new FileWriter(filename);
//           Gson gson = new GsonBuilder().setPrettyPrinting().create()) {
//         gson.toJson(this, writer);
//      }
//   }

   //TODO finish deserialize
   public static void deserialize() {

   }

   public Location getLocation() {
      return location;
   }


   public Player getPlayer() {
      return player;
   }


   public Inventory getInventory() {
      return inventory;
   }
}