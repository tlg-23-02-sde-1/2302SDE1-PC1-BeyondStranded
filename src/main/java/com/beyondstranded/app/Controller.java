package com.beyondstranded.app;

import com.beyondstranded.Combat;
import com.beyondstranded.Location;
import com.beyondstranded.NPC;
import com.beyondstranded.Player;
import com.util.apps.Prompter;

import java.util.*;

import static com.util.apps.Console.clear;

public class Controller {

    //fields
    private final Prompter prompter = new Prompter(new Scanner(System.in));
    private final Parser parser = new Parser();
    private final Introduction intro = new Introduction(prompter);
    private final Command commands = new Command();
    private final int maxHealth = 50;
    private final MidiPlayer midiPlayer = new MidiPlayer();

    // business methods
    public void start() {
        startGame();
    }

    private void startGame() {
        Thread introThread = new Thread(midiPlayer::playIntro);
        //introThread.start();
        //intro.showTitlePage();
        //intro.gameOption();
        //intro.showCoreStory();
        gameStarted();
        intro.gameOver();
    }

    private void gameStarted() {
        boolean gameOver = false;
        boolean hasPlayerWon = false;
        Map<String, Location> allLocation;
        allLocation = JsonDataLoader.parseLocationsFromFile();
        Map<String, NPC> allNPCS = commands.npcMap;
        List<String> items = new ArrayList<>();
        commands.startMapCommand();
        Map<String, Location> visitedLocation = new HashMap<>();
        Player player = new Player(JsonDataLoader.getLocationInfo("Awakening", allLocation), maxHealth, items, visitedLocation);
        player.getVisitedLocations().put(player.getLocation().getName(), player.getLocation());
        List<String> userInput;

        while (!gameOver) {
            //midiPlayer.playGamePlay();
            printLocationInfo(player.getLocation().getName(), allLocation);
            if (player.getLocation().getName().equals("Cave") && !allNPCS.get("hunter").isHasHelped()) {
                NPC npc = allNPCS.get("hunter");
                Combat combat = new Combat(player, npc);
                combat.startCombat();
            }
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
                    commands.lookCommand(userInput, player, allLocation);
                    break;
                case "talk":
                    commands.talkCommand(userInput, player, allNPCS);
                    break;
                case "map":
                    commands.showMapCommand();
                    break;
                case "get":
                    allLocation = commands.getCommand(userInput, player, allLocation);
                    break;
                case "drop":
                    allLocation = commands.dropCommand(userInput, player, allLocation);
                    break;
                case "teleport":
                    player = commands.teleportCommand(userInput, player, allLocation);
                    break;
                case "aid":
                    allNPCS = commands.aidCommand(userInput, player);
                    break;
                case "activate":
                    hasPlayerWon = commands.activateCommand(userInput, player);
                    gameOver = hasPlayerWon;
                    break;
                case "tie":
                    player = commands.tieCommand(userInput, player);
                    break;
                case "save":
                    commands.saveGameProgress(player);
                    break;
            }
            prompter.prompt("\nPress Enter to Continue:","","Invalid input. Only press Enter in your keyboard.\n");
        }

        if (hasPlayerWon) {
            intro.congratulations();
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
}