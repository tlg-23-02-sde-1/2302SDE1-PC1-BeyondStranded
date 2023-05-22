package com.beyondstranded.app;

import com.beyondstranded.Item;
import com.google.gson.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class JsonMod extends Controller{

    public static void jsonMod() throws FileNotFoundException {
        File input = new File("src/main/resources/JSON/items.txt");
        try {
            JsonElement fileElement = JsonParser.parseReader(new FileReader(input));
            JsonObject fileObject = fileElement.getAsJsonObject();

            // Extracting fields (items)
            JsonArray itemsArray = fileObject.getAsJsonArray("items");
            List<Item> items = new ArrayList<>();
            for (JsonElement itemElement : itemsArray) {
                JsonObject itemObject = itemElement.getAsJsonObject();

                //Extract
                String itemName = itemObject.get("name").getAsString();
                String itemUse = itemObject.get("use").getAsString();
                //String itemDescription = itemObject.get("description").getAsString();

                System.out.println("Item: " + itemName);
                System.out.println("Use: " + itemUse);
                //System.out.println("Description: " + itemDescription);
                System.out.println();

                // Modify
                itemObject.addProperty("name", "New Name");
                itemObject.addProperty("use", "New Use");

                // Update the Item
                Item item = new Item(itemName, itemUse); // Create a new item using the constructor
                items.add(item);
            }

            // Write the modified JSON back to the original file
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter writer = new FileWriter(input);
            writer.write(gson.toJson(fileElement));
            writer.close();
            } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Test jsonMod
    public static void main(String[] args) throws FileNotFoundException {
        // Call your testing method here
        jsonMod();
    }
}