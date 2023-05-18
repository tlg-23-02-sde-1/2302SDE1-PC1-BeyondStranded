package com.beyondstranded.app;

import java.io.IOException;
import java.sql.Array;
import java.util.*;

class Parser extends Controller{

    public static void parseCommand(List<String> wordlist) {
        String verb;
        String noun;
        List<String> commands = new ArrayList<>(Arrays.asList("take", "go", "look", "quit", "move", "advance",
                "travel", "walk", "inspect", "examine", "scan", "watch", "drop", "get", "listen", "build", "steal", "make", "talk"));
        List<String> objects = new ArrayList<>(Arrays.asList("tree", "head", "jungle", "cave", "radio", "first aid ",
                "kit", "torch", "waterfall", "cliff", "village", "shore", "forest", "pool", "north", "south", "east", "west", "sea",
                "up", "down", "shell", "driftwood", "berries", "eye", "sound", "crash", "helicopter", "peak", "ship",
                "hull", "help", "bottle", "water", "map", "fire", "tool", "friends", "chief", "healer", "hunter"));

        if (wordlist.size() != 2) {
            System.out.println("Only 2 word commands allowed!");
            return;
        }


        verb = wordlist.get(0).toLowerCase();
        noun = wordlist.get(1).toLowerCase();

        if (!commands.contains(verb)) {
            System.out.println(verb + " is not an option");
            return;
        }

        if (!objects.contains(noun)) {
            System.out.println(noun + " is not an option");
            return;
        }

    }

    public static List<String> wordList(String input) {
        String delims = "[\t ,.:;?!\"']+";
        List<String> strlist = new ArrayList<>();
        String[] words = input.split(delims);
        strlist.addAll(Arrays.asList(words));
        return strlist;
    }

    public static String runCommand(String inputstr) {
        List<String> wl;
        String s = "bad";
        String lowstr = inputstr.trim().toLowerCase();

        if (!lowstr.equals("q")) {
            if (lowstr.equals("")) {
                s = "Enter a command";
            } else {
                wl = wordList(lowstr);
                parseCommand(wl);
                s = "ok";
            }
        }
        return s;
    }


    //TEST
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Parser parser = new Parser();

        while (true) {
            System.out.print("Enter a command: ");
            String input = scanner.nextLine();

            if (input.equals("q")) {
                break;
            }

            String result = parser.runCommand(input);
            System.out.println(result);
        }

        scanner.close();
    }
}