package com.beyondstranded.app;

import com.beyondstranded.Item;
import com.beyondstranded.Location;
import com.beyondstranded.NPC;
import com.beyondstranded.Player;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.util.apps.Prompter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        Map<String, Location> allLocation;
        allLocation = parseLocationsFromFile();
        List<String> items = new ArrayList<>();
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
                    commands.talkCommand(userInput, player, parseNpcsFromFile());
                    break;
                case "show":
                    commands.showMapCommand(player.getLocation());
                    break;
                case "get":
                    allLocation = commands.getCommand(userInput, player, allLocation);
                    break;
                case "drop":
                    allLocation = commands.dropCommand(userInput, player, allLocation);
                    break;
            }
            prompter.prompt("\nPress Enter to Continue:","","Invalid input. Only press Enter in your keyboard.\n");
        }
    }

    private void printLocationInfo(String locationName, Map<String, Location> allLocations) {
        clear();
        Location currentLocation = allLocations.get(locationName);
        System.out.printf("Location: %s\n\n", currentLocation.getName());
        String[] description = currentLocation.getDescription().split("\\.");
        for (String sentence : description) {
            System.out.println(sentence.trim());
        }
        System.out.println("\nItems located here: " + currentLocation.getItems());
        if (!currentLocation.getNpc().isEmpty()) {
            System.out.println("\nNPC located here: " + currentLocation.getNpc().toString());
        }
    }

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
            // Parse the JSON to npc
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
            // Parse the JSON to LocationsWrapper
            List<Location> locationList = gson.fromJson(isr, new TypeToken<List<Location>>() {}.getType());

            return locationList.stream()
                    .collect(Collectors.toMap(Location::getName, Function.identity()));
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }
}