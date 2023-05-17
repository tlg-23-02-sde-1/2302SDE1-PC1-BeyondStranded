package com.beyondstranded.app;

import com.util.apps.Prompter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.util.apps.Console.*;

class Introduction {

    private static String rules;
    private static String introBanner;
    private static String newGame;

    private final Prompter prompter;

    Introduction(Prompter prompter) {
        this.prompter = prompter;
    }

    private void welcomePrompt() {
        blankLines(1);
        clear();
        String input = prompter.prompt("Enter your input: ","","This is not a valid option\n");
    }

    void gameOption() {
        blankLines(1);
        clear();
        System.out.println(newGame);
        String input = prompter.prompt("Enter your command: ","(?i)^new game$","This is not a valid option\n");
    }

    static {
        try {
            //rules = Files.readString(Path.of();
            //introBanner = Files.readString(Path.of());
            newGame = Files.readString(Path.of("src/main/resources/images/New Game Prompt.txt"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}