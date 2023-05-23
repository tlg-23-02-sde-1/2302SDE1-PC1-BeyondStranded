package com.beyondstranded.app;

import com.beyondstranded.*;
import com.google.gson.*;
import com.util.apps.Prompter;

import java.io.*;
import java.util.*;

import static com.util.apps.Console.clear;

public class Controller {

    //fields
    private final Prompter prompter = new Prompter(new Scanner(System.in));
    private final Parser parser = new Parser();
    private final Introduction intro = new Introduction(prompter);
    private final Command commands = new Command(prompter);
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
                    commands.helpCommand();
                    break;
                case "look":
                    commands.lookCommand(userInput, player);
                    break;
                case "talk":
                    commands.talkCommand(userInput, player, loadNPC());
                    break;
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
                currentLocation = location;
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

    static NPCWrapper loadNPC() {
        Gson gson = new Gson();
        NPCWrapper npcWrapper = null;

        try (InputStreamReader isr = new InputStreamReader(Controller.class.getResourceAsStream("/JSON/npc.txt"))) {
            // Parse the JSON to NPCWrapper
            npcWrapper = gson.fromJson(isr, NPCWrapper.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return npcWrapper;
    }

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