package com.beyondstranded.app;

import com.beyondstranded.Item;
import com.beyondstranded.Location;
import com.beyondstranded.NPC;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

class JsonDataLoader {

    static Location getLocationInfo(String locationName, Map<String, Location> allLocations) {
        return allLocations.get(locationName);
    }

    /**
     * Parses items from a file and returns a list of Item objects.
     *
     * @return List of Item objects parsed from the file.
     */
    static Map<String, Item> parseItemsFromFile() {
        Gson gson = new Gson();

        //noinspection ConstantConditions
        try (InputStreamReader isr = new InputStreamReader(Controller.class.getResourceAsStream("/JSON/items.txt"))) {
            // Parse the JSON to a List of Item
            List<Item> itemsList = gson.fromJson(isr, new TypeToken<List<Item>>() {}.getType());

            return itemsList.stream()
                    .collect(Collectors.toMap(Item::getName, Function.identity()));
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    static Map<String, NPC> parseNpcsFromFile() {
        Gson gson = new Gson();

        //noinspection ConstantConditions
        try (InputStreamReader isr = new InputStreamReader(Controller.class.getResourceAsStream("/JSON/npc.txt"))) {
            // Parse the JSON to a List of NPC
            List<NPC> npcList = gson.fromJson(isr, new TypeToken<List<NPC>>() {}.getType());

            return npcList.stream()
                    .collect(Collectors.toMap(NPC::getName, Function.identity()));
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    /**
     * Parses locations from a file and returns a list of Location objects.
     *
     * @return List of Location objects parsed from the file.
     */
    static Map<String, Location> parseLocationsFromFile() {
        Gson gson = new Gson();

        //noinspection ConstantConditions
        try (InputStreamReader isr = new InputStreamReader(Controller.class.getResourceAsStream("/JSON/locations.txt"))) {
            // Parse the JSON to List of Locations
            List<Location> locationList = gson.fromJson(isr, new TypeToken<List<Location>>() {}.getType());

            return locationList.stream()
                    .collect(Collectors.toMap(Location::getName, Function.identity()));
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }
}