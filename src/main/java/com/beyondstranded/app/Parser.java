package com.beyondstranded.app;

import com.beyondstranded.Location;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Parser {

    private final List<String> goCommandList = loadCommandMap("Go");
    private final List<String> dropCommandList = loadCommandMap("Drop");
    private final List<String> getCommandList = loadCommandMap("Get");
    private final List<String> lookCommandList = loadCommandMap("Look");
    private final List<String> talkCommandList = loadCommandMap("Talk");

    boolean parseCommand(List<String> wordlist) {
        boolean result = false;
        String verb;
        String noun;

        List<String> directions = new ArrayList<>(List.of("south","east","west","north"));
        List<String> npc = new ArrayList<>(JsonDataLoader.parseNpcsFromFile().keySet());
        List<String> items = new ArrayList<>(JsonDataLoader.parseItemsFromFile().keySet());
        // Teleport locations
        List<String> locations = JsonDataLoader.parseLocationsFromFile().values().stream()
                .map(Location::getName)
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        List<String> validCommands = Stream.of(goCommandList,dropCommandList,getCommandList,lookCommandList,talkCommandList)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        // Added locations to objects
        List<String> objects = Stream.of(directions,npc,items,locations)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        // Add a way to teleport
        validCommands.add("teleport");

        if (wordlist.size() != 2) {
            System.out.println("ERROR: Only 2 word commands allowed!");
        }
        else {
            verb = wordlist.get(0).toLowerCase();
            noun = wordlist.get(1).toLowerCase();
            if (!validCommands.contains(verb)) {
                System.out.println(verb + " is not an option");
            }
            else if (!objects.contains(noun)) {
                System.out.println(noun + " is not an option");
            }
            else {
                result = true;
            }
        }
        return result;
    }

    List<String> wordList(String input) {
        String delims = "\\W+";
        List<String> strlist = new ArrayList<>();
        String[] words = input.split(delims);
        strlist.addAll(Arrays.asList(words));
        return strlist;
    }

    List<String> userCommand() {
        List<String> userInput = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;

        label:
        while (!validInput) {
            System.out.print("\nEnter a command: ");
            String input = scanner.nextLine().trim().toLowerCase();

            switch (input) {
                case "quit":
                    userInput.add(0, "quit");
                    break label;
                case "":
                    System.out.println("Error\n");
                    System.out.printf("\nInvalid Input. Input is empty. Input: %s", input);
                    break;
                case "help":
                    userInput.add(0, "help");
                    break label;
                case "map":
                    userInput.add(0, "map");
                    break label;
                default:
                    List<String> wl = wordList(input);
                    wl = checkCommand(wl);
                    validInput = parseCommand(wl);
                    if (validInput) {
                        userInput = wl;
                        System.out.println("status: 200");
                    }
                    break;
            }
        }
        //scanner.close();
        return userInput;
    }

    List<String> checkCommand(List<String> userInput) {
        String firstWord = userInput.get(0);

        if (goCommandList.contains(firstWord)) {
            userInput.set(0, "go");
        }
        else if (dropCommandList.contains(firstWord)) {
            userInput.set(0, "drop");
        }
        else if (getCommandList.contains(firstWord)) {
            userInput.set(0, "get");
        }
        else if (lookCommandList.contains(firstWord)) {
            userInput.set(0, "look");
        }
        else if (talkCommandList.contains(firstWord)) {
            userInput.set(0, "talk");
        }
        else if (firstWord.contains("teleport")) {
            for (Location location : JsonDataLoader.parseLocationsFromFile().values()) {
                if (location.getName().toLowerCase().contains(userInput.get(1))) {
                    userInput.set(1, location.getName());
                }
            }
        }
        return userInput;
    }

    public List<String> loadCommandMap(String command) throws RuntimeException {
        List<String> goCommand = new ArrayList<>();
        String commandPath = "/Commands CSV/" + command + "-Command.csv";

        //noinspection ConstantConditions
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(commandPath)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                goCommand.addAll(Arrays.asList(tokens));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return goCommand;
    }

    //TEST
    public static void main(String[] args) {
        Parser parser = new Parser();
        List<String> input = parser.userCommand();
        System.out.println(input);
    }
}