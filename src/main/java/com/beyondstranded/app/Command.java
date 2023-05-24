package com.beyondstranded.app;

import com.beyondstranded.*;
import com.util.apps.Prompter;

import java.util.List;
import java.util.Map;
import java.util.Random;

class Command {
    private List<String> commands;
    private final Map<String, Item> itemsMap = Controller.loadItemsIntoMap();
    private final Prompter prompter;

    public Command(Prompter prompter) {
        this.prompter = prompter;
    }

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

    void talkCommand(List<String> command, Player player, NPCWrapper npcWrapper) {
        Random rand = new Random();
        NPC roomNPC = null;
        for (NPC npc : npcWrapper.getNpc()) {
            if (command.get(1).equalsIgnoreCase(npc.getName()) &&
                    player.getLocation().getName().equalsIgnoreCase(npc.getLocation())) {
                roomNPC = npc;
            }
        }
        if (roomNPC != null) {
            List<String> npcDialogue = roomNPC.getDialogue();
            String randomDialogue = npcDialogue.get(rand.nextInt(npcDialogue.size()));
            System.out.println(randomDialogue);
        }
        else {
            System.out.printf("\nThere is no %s located here.\n", command.get(1));
        }
    }

    void getCommand(List<String> command, Player player) {

    }

    void showMapCommand(List<String> command, Player player) {
        Location currentLocation = player.getLocation();

    }

    void helpCommand() {
        Introduction intro = new Introduction(prompter);
        intro.showHelp();
    }
}