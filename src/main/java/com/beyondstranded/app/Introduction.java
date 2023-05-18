package com.beyondstranded.app;

import com.util.apps.Prompter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.util.apps.Console.*;

class Introduction {

    private static String banner;
    private static String objective;
    private static String welcome;
    private static String newGame;
    private static String story;


    private final Prompter prompter;

    Introduction(Prompter prompter) {
        this.prompter = prompter;
    }

    void showTitlePage() {
        showcasePrompt(banner);
    }

    void gameOption() {
        clear();
        blankLines(1);
        System.out.println(newGame);
        prompter.prompt("\n\t\t\t\t\tEnter your command: ","(?i)^new game$","\t\t\t\t\tThis is not a valid option\n");
    }

    void showCoreStory() {
        showcasePrompt(welcome);
        showcasePrompt(story);
        showcasePrompt(objective);
    }

    void showcasePrompt(String prompt) {
        clear();
        blankLines(1);
        System.out.println(prompt);
        prompter.prompt("\n\t\t\t\t\tPress Enter to Continue:","","\t\t\t\t\tInvalid input. Only press Enter in your keyboard.\n");
    }

    static {
        try {
            banner = Files.readString(Path.of("src/main/resources/ASCII_Art/Banner.txt"));
            newGame = Files.readString(Path.of("src/main/resources/images/New Game Prompt.txt"));
            story = Files.readString(Path.of("src/main/resources/images/Storyline Prompt.txt"));
            objective = Files.readString(Path.of("src/main/resources/images/Objective Prompt.txt"));
            welcome = Files.readString(Path.of("src/main/resources/images/Welcome Prompt.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}