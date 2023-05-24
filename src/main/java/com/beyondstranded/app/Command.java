package com.beyondstranded.app;

import com.beyondstranded.*;
import com.util.apps.Prompter;

import java.util.List;
import java.util.Map;
import java.util.Random;

class Command {
    private final Map<String, Item> itemsMap = Controller.parseItemsFromFile();
    private final Prompter prompter;
    private Map<String, Location> currentRoomLocation = Controller.parseLocationsFromFile();

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

    void talkCommand(List<String> command, Player player, Map<String, NPC> MapOfNPC) {
        Random rand = new Random();
        NPC roomNPC = MapOfNPC.get(command.get(1).toLowerCase());
        if (roomNPC != null && roomNPC.getLocation().equals(player.getLocation().getName())) {
            List<String> npcDialogue = roomNPC.getDialogue();
            String randomDialogue = npcDialogue.get(rand.nextInt(npcDialogue.size()));
            System.out.println("Dialogue of " + roomNPC.getName() + " : " + randomDialogue);
        }
        else {
            System.out.printf("\nThere is no %s located here.\n", command.get(1));
        }
    }

    void getCommand(List<String> command, Player player) {
//        String itemName = command.get(1);
//        Location currentLocation = player.getLocation();
//        List<String> locationItems = currentLocation.getItems();
        Location currentLocation = currentRoomLocation.get(player.getLocation().getName());
        Map<String, Item> itemMap = Controller.parseItemsFromFile();
        int index = 0;
        List<String> currentItemsInLocation = currentRoomLocation.get(player.getLocation().getName()).getItems();
        if (currentLocation.getItems().contains(command.get(1))) {
            Item userItem = itemMap.get(command.get(1));
            player.addItemToInventory(userItem.getName());
            for (int i = 0; i < currentItemsInLocation.size(); i++) {
                if (currentItemsInLocation.get(i).equalsIgnoreCase(command.get(1))) {
                    index = i;
                }
            }
            currentItemsInLocation.remove(index);
        }
        else {
            System.out.println("There is no " + command.get(1) + " in the location.");
        }


//        //List<String> currentRoomItems = player.getLocation().getItems();
//        if (allItemsInCurrentLocation.contains(command.get(1))) {
//            player.addItemToInventory(allItemsInCurrentLocation.);
//            for (String items : currentRoomLocation.get(player.getLocation().getName()).getItems()) {
//
//            }
//            currentRoomLocation.get(player.getLocation().getName()).getItems().remove()
//        }
//        else {
//            System.out.println("There is no " + command.get(1) + " in the location.");
//        }
//
//        // Check if the item is avaliable in the current location
//        boolean itemExists = false;
//        for (String item : locationItems){
//            if(item.getName().equalsIgnoreCase(itemName)){
//                itemExists = true;
//                break;
//            }
//        }
//        if (itemExists){
//            // Get the item object from the location
//            Item item = currentLocation.getItemByName(itemName);
//            if(item ! = null){
//                // Add the item to the player's inventory
//                player.addItemToInventory(item);
//                System.out.println("You picked up " + itemName + ".");
//                // Remove the item from the location
//                currentLocation.removeItem(item);
//            }
//        }else{
//            System.out.println("There is no " + itemName + " in the location.");
//        }
//        Item item = itemsMap.get(itemName.toLowerCase());
//        if(item != null){
//            player.getItem(item);
//            System.out.println("You have obtained " + item.getName());
//        }
    }

    void showMapCommand(List<String> command, Player player) {
        Location currentLocation = player.getLocation();

    }

    void helpCommand() {
        Introduction intro = new Introduction(prompter);
        intro.showHelp();
    }
}