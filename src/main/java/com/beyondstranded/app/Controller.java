package com.beyondstranded.app;

import com.beyondstranded.*;
import com.google.gson.*;
import com.util.apps.Prompter;


import static com.util.apps.Console.*;
import java.io.*;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static com.beyondstranded.app.Introduction.readResource;
import static com.util.apps.Console.clear;

public class Controller {

    //fields
    private final Prompter prompter = new Prompter(new Scanner(System.in));
    private final Parser parser = new Parser();
    private final Introduction intro = new Introduction(prompter);
    private final Commands commands = new Commands();


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
        Player player = new Player(getLocationInfo("Awakening", allLocation));
        List<String> userInput;

        while (!gameOver) {
            printLocationInfo(player.getLocation().getName(), allLocation);
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
            }
        }
    }

    private void printLocationInfo(String locationName, List<Location> allLocations) {
        clear();
        for (Location location : allLocations) {
            if (location.getName().equals(locationName)) {
                System.out.println("Location: " + location.getName());
                System.out.println("Description: " + location.getDescription());
                System.out.println("What is located here: " + location.getItems());
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