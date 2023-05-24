package com.beyondstranded.app;

import com.beyondstranded.*;
import com.util.apps.Prompter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.util.apps.Console.blankLines;
import static com.util.apps.Console.clear;

class Command {
    private final Map<String, Item> itemsMap = JsonDataLoader.parseItemsFromFile();
    private final Prompter prompter;

    public Command(Prompter prompter) {
        this.prompter = prompter;
    }

    void commandCheck(List<String> command) {

    }

    Player goCommand(List<String> command, Player player, Map<String, Location> allLocations) {
        // Get the map of directions from the current location
        Map<String, String> directions = player.getLocation().getDirections();

        // If the user-provided direction is a key in the map,
        // get the location name it maps to
        if (directions.containsKey(command.get(1))){
            String newLocationName = directions.get(command.get(1));

            // Get the Location object corresponding to the new location name
            Location newLocation = JsonDataLoader.getLocationInfo(newLocationName, allLocations);

            // Set the player's location to the new location
            player.setLocation(newLocation);
        }
        else {
            System.out.println("You can't go " + command.get(1) + " from here.");
        }
        return player;
    }

    void lookCommand(List<String> command, Player player, Map<String, Location> allLocations) {

        List<String> locationItems = allLocations.get(player.getLocation().getName()).getItems();
        List<String> playerInv = player.getInventory();
        List<String> mergedList = Stream.concat(locationItems.stream(), playerInv.stream())
                .collect(Collectors.toList());

        for (String itemName : mergedList) {
            if (command.get(1).equalsIgnoreCase(itemName)) {
                Item item = itemsMap.get(itemName.toLowerCase());
                if (item != null) {
                    System.out.println(item.getDescription());
                }
                else {
                    System.out.println("There is no " + command.get(1) + " to look at.");
                }
            }
        }
    }

    void talkCommand(List<String> command, Player player, Map<String, NPC> MapOfNPC) {
        Random rand = new Random();
        NPC roomNPC = MapOfNPC.get(command.get(1).toLowerCase());
        if (roomNPC != null && roomNPC.getLocation().equals(player.getLocation().getName())) {
            List<String> npcDialogue = roomNPC.getDialogue();
            String randomDialogue = npcDialogue.get(rand.nextInt(npcDialogue.size()));
            System.out.println("Dialogue of " + roomNPC.getName() + " : " + randomDialogue);
        }
        else if (roomNPC == null) {
            System.out.printf("\nYou can't talk to %s here.\n", command.get(1));
        }
        else {
            System.out.printf("\nThere is no %s located here.\n", command.get(1));
        }
    }

    Map<String,Location> getCommand(List<String> command, Player player, Map<String, Location> currentRoomLocation) {
        Location currentLocation = currentRoomLocation.get(player.getLocation().getName());
        Map<String, Item> itemMap = JsonDataLoader.parseItemsFromFile();
        int index = 0;
        List<String> currentItemsInLocation = currentLocation.getItems();
        if (currentItemsInLocation.contains(command.get(1))) {
            Item userItem = itemMap.get(command.get(1));
            player.addItemToInventory(userItem.getName());
            for (int i = 0; i < currentItemsInLocation.size(); i++) {
                if (currentItemsInLocation.get(i).equalsIgnoreCase(command.get(1))) {
                    index = i;
                }
            }
            currentItemsInLocation.remove(index);
            currentLocation.setItems(currentItemsInLocation);
        }
        else {
            System.out.println("There is no " + command.get(1) + " in the location.");
        }
        currentRoomLocation.put(player.getLocation().getName(),currentLocation);
        return currentRoomLocation;
    }

    Map<String,Location> dropCommand(List<String> command, Player player, Map<String, Location> currentRoomLocation) {
        Location currentLocation = currentRoomLocation.get(player.getLocation().getName());
        Map<String, Item> itemMap = JsonDataLoader.parseItemsFromFile();
        List<String> currentItemsInLocation = currentLocation.getItems();
        if (!currentItemsInLocation.contains(command.get(1)) && player.getInventory().contains(command.get(1))) {
            Item userItem = itemMap.get(command.get(1));
            player.removeItemFromInventory(userItem.getName());
            currentItemsInLocation.add(userItem.getName());
            currentLocation.setItems(currentItemsInLocation);
        }
        else {
            System.out.println("There is no " + command.get(1) + " in your inventory to drop.");
        }
        currentRoomLocation.put(player.getLocation().getName(),currentLocation);
        return currentRoomLocation;
    }

    void showMapCommand(Location player) {
        try {
            clear();
            String path = "/ASCII_Art/Map" + player.getName() + ".txt";
            String showMap = Introduction.readResource(path);
            System.out.println(showMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void helpCommand() {
        try {
            clear();
            blankLines(1);
            System.out.println(Introduction.readResource("/images/Help.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}