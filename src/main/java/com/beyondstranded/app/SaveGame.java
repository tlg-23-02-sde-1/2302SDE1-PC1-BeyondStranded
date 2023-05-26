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

class SaveGame implements Serializable {
   Location location;
   Player player;
   Inventory inventory;

   public SaveGame(Location location, Player player, Inventory inventory) {
      this.location = location;
      this.player = player;
      this.inventory = inventory;
   }

   public void serialize(String filename) throws IOException {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      String json = gson.toJson(this);

      FileWriter writer = new FileWriter(filename);
      writer.write(json);
      writer.close();
   }
}