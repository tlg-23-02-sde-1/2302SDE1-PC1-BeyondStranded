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

    public static void deleteItem(String itemName) throws FileNotFoundException {
        File input = new File("src/main/resources/JSON/items.txt");
        try {
            JsonElement fileElement = JsonParser.parseReader(new FileReader(input));
            JsonObject fileObject = fileElement.getAsJsonObject();

            // Extracting fields .....My example of items. Not sure what exactly we will want to delete
            JsonArray itemsArray = fileObject.getAsJsonArray("items");
            List<Item> items = new ArrayList<>();
            for (int i = 0; i < itemsArray.size(); i++) {
                JsonObject itemObject = itemsArray.get(i).getAsJsonObject();

                // Extract item details "name"
                String currentItemName = itemObject.get("name").getAsString();

                if (currentItemName.equals(itemName)) {
                    // Item found, remove it from the array
                    itemsArray.remove(i);
                    i--;
                } else {
                    // Item not found, add it to the list
                    String itemUse = itemObject.get("use").getAsString();
                    Item item = new Item(currentItemName, itemUse);
                    items.add(item);
                }
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