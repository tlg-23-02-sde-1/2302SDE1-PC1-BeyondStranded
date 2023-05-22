package com.beyondstranded.app;

import com.beyondstranded.Item;
import com.beyondstranded.Location;
import com.beyondstranded.Player;

import java.util.List;
import java.util.Map;

class Commands {
    private List<String> commands;
    private final Map<String, Item> itemsMap = Controller.loadItemsIntoMap();

    void commandCheck(List<String> command) {

    }

    Player goCommand(List<String> command, Player player, List<Location> allLocations) {
        // Get the map of directions from the current location
        Map<String, String> directions = player.getLocation().getDirections();

        // If the user-provided direction is a key in the map,
        // get the location name it maps to
        if (directions.containsKey(command.get(1))){
            String newLocationName = directions.get(command.get(1));

            // Get the Location object corresponding to the new location name
            Location newLocation = Controller.getLocationInfo(newLocationName, allLocations);

            // Set the player's location to the new location
            player.setLocation(newLocation);
        }
        else {
            System.out.println("You can't go " + command.get(1) + " from here.");
        }
        return player;
    }

    void lookCommand(List<String> command, Player player) {
        List<String> itemNames = player.getLocation().getItems();
        for (String itemName : itemNames) {
            if (command.get(1).equalsIgnoreCase(itemName)) {
                Item item = itemsMap.get(itemName.toLowerCase());
                if (item != null) {
                    System.out.println(item.getDescription());
                }
            }
        }
    }

    void getCommand(List<String> command, Player player) {

    }

    void helpCommand(List<String> command, Player player) {

    }
}