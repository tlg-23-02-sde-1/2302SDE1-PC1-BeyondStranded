package com.beyondstranded.app;

import com.beyondstranded.*;
import com.google.gson.*;
import com.util.apps.Prompter;

import java.io.*;
import java.util.*;

import static com.beyondstranded.app.Introduction.readResource;
import static com.util.apps.Console.clear;

public class Controller {

    //fields
    private final Prompter prompter = new Prompter(new Scanner(System.in));
    private final Parser parser = new Parser();
    private final Introduction intro = new Introduction(prompter);
    private final Commands commands = new Commands();
    private final int maxHealth = 50;
    private Player player;


    // business methods
    public void start() {
        startGame();
    }

    private void startGame() {
        intro.showTitlePage();
        intro.gameOption();
        intro.showCoreStory();
        gameStarted();
        intro.gameOver();
    }

    private void displayHelp() {
        try {
            String helpText = readResource("/images/help.txt");
            intro.showHelp(helpText);
        } catch (IOException e) {
            System.out.println("Unable to display help.");
        }
    }

    private void gameStarted() {
        boolean gameOver = false;
        List<Location> allLocation;
        allLocation = parseLocationsFromFile();
        List<Item> items = new ArrayList<>();
        player = new Player(getLocationInfo("Awakening", allLocation),maxHealth, items);
        List<String> userInput;

        while (!gameOver) {
            printLocationInfo(player.getLocation().getName(), allLocation);
            System.out.println("\nPlayer Health: " + player.getHealth());
            System.out.println("\nPlayer Inventory: " + player.getInventory());
            userInput = parser.userCommand();
            switch (userInput.get(0)) {
                case "go":
                    player = commands.goCommand(userInput, player, allLocation);
                    break;
                case "quit":
                    gameOver = true;
                    break;
                case "help":
                    displayHelp();
                    break;
                case "look":
                    commands.lookCommand(userInput, player);
                    break;
                case "talk":
                    // Create method to talk in Commands
                    System.out.println(loadNPC().getDescription());
            }
        }
    }

    private void printLocationInfo(String locationName, List<Location> allLocations) {
        clear();
        for (Location location : allLocations) {
            if (location.getName().equals(locationName)) {
                System.out.printf("Location: %s\n\n", location.getName());
                String[] description = location.getDescription().split("\\.");
                for (String sentence : description) {
                    System.out.println(sentence.trim());
                }
                System.out.println("\nWhat is located here: " + location.getItems());
            }
        }
    }

    static Location getLocationInfo(String locationName, List<Location> allLocations) {
        Location currentLocation = null;
        for (Location location : allLocations) {
            if (location.getName().equals(locationName)) {
                return currentLocation = location;
            }
        }
        return currentLocation;
    }

    static Map<String, Item> loadItemsIntoMap() {
        Gson gson = new Gson();

        try (InputStreamReader isr = new InputStreamReader(Controller.class.getResourceAsStream("/JSON/items.txt"))) {
            // Parse the JSON to ItemsWrapper
            ItemsWrapper itemsWrapper = gson.fromJson(isr, ItemsWrapper.class);

            Map<String, Item> itemsMap = new HashMap<>();
            for (Item item : itemsWrapper.getItems()) {
                itemsMap.put(item.getName().toLowerCase(), item);
            }

            return itemsMap;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    NPC loadNPC() {
        Gson gson = new Gson();
        NPC roomNPC = null;

        try (InputStreamReader isr = new InputStreamReader(getClass().getResourceAsStream("/JSON/npc.txt"))) {
            // Parse the JSON to ItemsWrapper
            NPCWrapper npcWrapper = gson.fromJson(isr, NPCWrapper.class);
            for (NPC npc : npcWrapper.getNpc()) {
                if (player.getLocation().getName().equals(npc.getLocation())) {
                    roomNPC = npc;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return roomNPC;
    }

    /**
     * Parses locations from a file and returns a list of Location objects.
     *
     * @return List of Location objects parsed from the file.
     */
        List<Location> parseLocationsFromFile() {
        Gson gson = new Gson();

        try (InputStreamReader isr = new InputStreamReader(getClass().getResourceAsStream("/JSON/locations.txt"))) {
            // Parse the JSON to LocationsWrapper
            LocationsWrapper locationsWrapper = gson.fromJson(isr, LocationsWrapper.class);

            return locationsWrapper.getLocations();
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    /**
     * Parses items from a file and returns a list of Item objects.
     *
     * @return List of Item objects parsed from the file.
     */
    List<Item> parseItemsFromFile() {
        Gson gson = new Gson();

        try (InputStreamReader isr = new InputStreamReader(getClass().getResourceAsStream("/JSON/items.txt"))) {
            // Parse the JSON to LocationsWrapper
            ItemsWrapper itemsWrapper = gson.fromJson(isr, ItemsWrapper.class);

            return itemsWrapper.getItems();
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}