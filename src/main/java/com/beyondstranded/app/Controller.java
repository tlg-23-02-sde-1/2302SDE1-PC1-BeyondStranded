package com.beyondstranded.app;

import com.beyondstranded.*;
import com.google.gson.Gson;
import com.util.apps.Prompter;

import java.io.*;
import java.util.*;

import static com.util.apps.Console.clear;

public class Controller {

    //fields
    private final Prompter prompter = new Prompter(new Scanner(System.in));
    private final Parser parser = new Parser();

    // business methods
    public void start() {
        startGame();
    }

    private void startGame() {
        Introduction intro = new Introduction(prompter);
        intro.showTitlePage();
        intro.gameOption();
        intro.showCoreStory();
        gameStarted();
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
            if (userInput.get(0).equals("go")) {
                // Get the map of directions from the current location
                Map<String, String> directions = player.getLocation().getDirections();

                // If the user-provided direction is a key in the map,
                // get the location name it maps to
                if (directions.containsKey(userInput.get(1))) {
                    String newLocationName = directions.get(userInput.get(1));

                    // Get the Location object corresponding to the new location name
                    Location newLocation = getLocationInfo(newLocationName, allLocation);

                    // Set the player's location to the new location
                    player.setLocation(newLocation);
                } else {
                    System.out.println("You can't go " + userInput.get(1) + " from here.");
                }
            // if at any time the player types quit the game will quit
            }else if(userInput.get(0).equals("quit")){
                gameOver = true;
                System.out.println("Exiting the game. Goodbye!");
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

    private Location getLocationInfo(String locationName, List<Location> allLocations) {
        Location currentLocation = null;
        for (Location location : allLocations) {
            if (location.getName().equals(locationName)) {
                return currentLocation = location;
            }
        }
        return currentLocation;
    }

    private List<Location> parseLocationsFromFile() {
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

    //test function for modifying the Json
    private List<Item> modifyJsonItem() {
        Gson gson = new Gson();
        // Read the JSON file
        try (InputStreamReader isr = new InputStreamReader(getClass().getResourceAsStream("/JSON/items.txt"))){

            // Parse the JSON string
            ItemsWrapper itemsWrapper = gson.fromJson(isr, ItemsWrapper.class);

            return itemsWrapper.getItems();
            // Modify the JSON data (writer)

            // Write the updated JSON to a file

        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}