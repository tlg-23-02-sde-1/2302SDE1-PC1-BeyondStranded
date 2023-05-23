package com.beyondstranded.app;

import java.util.*;

class Parser {

    public static boolean parseCommand(List<String> wordlist) {
        boolean result = false;
        String verb;
        String noun;
        List<String> commands = new ArrayList<>(Arrays.asList("take", "go", "look", "quit", "move", "advance",
                "travel", "walk", "inspect", "examine", "scan", "watch", "drop", "get", "listen", "build", "steal", "make", "talk", "exit"));
        List<String> objects = new ArrayList<>(Arrays.asList("tree", "head", "jungle", "cave", "radio", "first aid ",
                "kit", "torch", "waterfall", "cliff", "village", "shore", "forest", "pool", "north", "south", "east", "west", "sea",
                "up", "down", "shell", "driftwood", "berries", "eye", "sound", "crash", "helicopter", "peak", "ship",
                "hull", "help", "bottle", "water", "map", "fire", "tool", "rope", "friends", "chief", "healer", "hunter"));

        if (wordlist.size() != 2) {
            System.out.println("ERROR: Only 2 word commands allowed!");
        }
        else {
            verb = wordlist.get(0).toLowerCase();
            noun = wordlist.get(1).toLowerCase();
            if (!commands.contains(verb)) {
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

    public static List<String> wordList(String input) {
        String delims = "\\W+";
        List<String> strlist = new ArrayList<>();
        String[] words = input.split(delims);
        strlist.addAll(Arrays.asList(words));
        return strlist;
    }

    public List<String> userCommand() {
        List<String> userInput = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;

        while (!validInput) {
            System.out.print("\nEnter a command: ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("quit")) {
                userInput.add(0,"quit");
                break;
            }
            else if(input.equals("")) {
                System.out.println("Error\n");
                System.out.printf("\nInvalid Input. Input is empty. Input: %s", input);
            }
            else if(input.equals("help")) {
                userInput.add(0,"help");
                break;
            }
            else {
                List<String> wl = wordList(input);
                validInput = parseCommand(wl);
                if (validInput) {
                    userInput = wl;
                    System.out.println("status: 200");
                }
            }
        }

        //scanner.close();
        return userInput;
    }

    //TEST
    public static void main(String[] args) {
        Parser parser = new Parser();
        List<String> input = parser.userCommand();
        System.out.println(input);
    }
}