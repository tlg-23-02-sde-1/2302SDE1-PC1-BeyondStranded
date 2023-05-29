package com.beyondstranded.app;

import com.beyondstranded.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.util.apps.Console.blankLines;
import static com.util.apps.Console.clear;

class Command {
    private final Map<String, Item> itemsMap = JsonDataLoader.parseItemsFromFile();
    private final GameMap map = new GameMap();

    Player goCommand(List<String> command, Player player, Map<String, Location> allLocations) {
        // Reset the previous location
        try {
            map.resetPreviousLocation();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get the map of directions from the current location
        Map<String, String> directions = player.getLocation().getDirections();

        // If the user-provided direction is a key in the map, get the location name it maps to
        if (directions.containsKey(command.get(1))) {
            String newLocationName = directions.get(command.get(1));

            // Get the Location object corresponding to the new location name
            Location newLocation = JsonDataLoader.getLocationInfo(newLocationName, allLocations);

            // Set the player's location to the new location and update gameMap
            map.setGameMap(player, newLocationName, newLocation);
            player.setLocation(newLocation);
        } else {
            System.out.println("You can't go " + command.get(1) + " from here.");
        }
        return player;
    }

    Player teleportCommand(List<String> command, Player player, Map<String, Location> allLocations) {
        if (allLocations.containsKey(command.get(1))) {
            player.setLocation(allLocations.get(command.get(1)));
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
                } else {
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
        } else if (roomNPC == null) {
            System.out.printf("\nYou can't talk to %s here.\n", command.get(1));
        } else {
            System.out.printf("\nThere is no %s located here.\n", command.get(1));
        }
    }

    Map<String, Location> getCommand(List<String> command, Player player, Map<String, Location> currentRoomLocation) {
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
        } else {
            System.out.println("There is no " + command.get(1) + " in the location.");
        }
        currentRoomLocation.put(player.getLocation().getName(), currentLocation);
        return currentRoomLocation;
    }

    Map<String, Location> dropCommand(List<String> command, Player player, Map<String, Location> currentRoomLocation) {
        Location currentLocation = currentRoomLocation.get(player.getLocation().getName());
        Map<String, Item> itemMap = JsonDataLoader.parseItemsFromFile();
        List<String> currentItemsInLocation = currentLocation.getItems();
        if (!currentItemsInLocation.contains(command.get(1)) && player.getInventory().contains(command.get(1))) {
            Item userItem = itemMap.get(command.get(1));
            player.removeItemFromInventory(userItem.getName());
            currentItemsInLocation.add(userItem.getName());
            currentLocation.setItems(currentItemsInLocation);
        } else {
            System.out.println("There is no " + command.get(1) + " in your inventory to drop.");
        }
        currentRoomLocation.put(player.getLocation().getName(), currentLocation);
        return currentRoomLocation;
    }

    void startMapCommand() {
        map.startMap();
        try {
            map.visitLocation("Awakening", 23, 12);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void showMapCommand() {
        clear();
        map.printMap();
        System.out.println("Key: ");
        System.out.print("P = Player Location");
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

    Map<String, NPC> aidCommand(List<String> command, Player player, Map<String, NPC> npcs) {
        List<String> npcNames = npcs.values().stream()
                .filter((NPC) -> !NPC.isHasHelped())
                .map(NPC::getName)
                .collect(Collectors.toList());

        if (npcNames.size() > 0 && npcNames.contains(command.get(1))) {
            int index = npcNames.indexOf(command.get(1));
            NPC aidNPC = npcs.get(npcNames.get(index));

            // Conditions to Aid the Healer
            if (aidNPC.getName().equals("healer") && player.getInventory().contains("bandages")) {
                aidNPC.getAidDialogue().forEach(System.out::println);
                player.getInventory().remove("bandages");
                List<String> npcItems = aidNPC.getItems();
                player.getInventory().addAll(npcItems);
                npcs.get(aidNPC.getName()).setHasHelped(true);
            } else if (aidNPC.getName().equals("healer")) {
                System.out.println(aidNPC.getDialogue().get(1));
            } else if (aidNPC.isHasHelped()) {
                System.out.println("Thank you traveler for helping me earlier. I do not require further aid.");
            }

            // Conditions to Aid the Chief
            if (npcNames.size() == 1 && player.getInventory().contains("talisman")) {
                aidNPC.getAidDialogue().forEach(System.out::println);
                player.getInventory().remove("talisman");
                List<String> npcItems = aidNPC.getItems();
                player.getInventory().addAll(npcItems);
                npcs.get(aidNPC.getName()).setHasHelped(true);
            } else if (npcNames.size() == 1) {
                System.out.println("Thank you traveler for your help with our village healer");
                System.out.println("I wish I could be of more help in your journey.");
                System.out.println("Alas, since our Talisman was stolen a few days ago.");
                System.out.println("We have been on lookout for it as it is sacred to our people.");
                System.out.println("If you manage to find it in your travels around the island. Please return it to us.");
            }

            // Conditions to Aid the Hunter for Testing Purpose Only. Future implementation will be from combat.
            if (aidNPC.getName().equals("hunter")) {
                aidNPC.getAidDialogue().forEach(System.out::println);
                List<String> npcItems = aidNPC.getItems();
                player.getInventory().addAll(npcItems);
                npcs.get(aidNPC.getName()).setHasHelped(true);
            }
        }
        return npcs;
    }

    boolean activateCommand(List<String> command, Player player) {
        boolean gameOver = false;

        if (player.getLocation().getName().equals("Ship Wreck") &&
                player.getInventory().contains("technology") &&
                command.get(1).equals("technology")) {
            System.out.println("You have activated the beacon and were sent home.");
            gameOver = true;
        } else if (player.getInventory().contains("technology") && command.get(1).equals("technology")) {
            System.out.println("The technology starts to spin up for 2 secs but however stops. You wonder how to start this.");
        } else {
            System.out.println("You can not activate " + command.get(1));
        }
        return gameOver;
    }
}