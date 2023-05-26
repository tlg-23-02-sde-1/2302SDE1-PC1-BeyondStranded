package com.beyondstranded.app;

import com.util.apps.Prompter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.util.apps.Console.*;

public class Introduction {

    private static String banner;
    private static String objective;
    private static String welcome;
    private static String newGame;
    private static String continueGame;
    private static String story;
    private static String winCondition;
    private static String lossCondition;
    private static String gameOver;


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
        System.out.println(continueGame); //adding as a feature to continue game state
        prompter.prompt("\n\t\t\tEnter your command: ","(?i)^new game$","\t\t\tThis is not a valid option\n");
    }

    void gameOver() {
        clear();
        blankLines(1);
        System.out.println(gameOver);
    }

    void showCoreStory() {
        showcasePrompt(welcome);
        showcasePrompt(story);
        showcasePrompt(objective);
        showcasePrompt(winCondition);
        showcasePrompt(lossCondition);
    }

    void showcasePrompt(String prompt) {
        clear();
        blankLines(1);
        System.out.println(prompt);
        prompter.prompt("\n\t\t\tPress Enter to Continue:","","\t\t\tInvalid input. Only press Enter in your keyboard.\n");
    }

    static {
        try {
            banner = readResource("/ASCII_Art/Banner.txt");
            newGame = readResource("/images/New Game Prompt.txt");
            continueGame = readResource("/saveFile.txt");
            story = readResource("/images/Storyline Prompt.txt");
            objective = readResource("/images/Objective Prompt.txt");
            welcome = readResource("/images/Welcome Prompt.txt");
            winCondition = readResource("/images/Win Condition Prompt.txt");
            lossCondition = readResource("/images/Loss Condition Prompt.txt");
            gameOver = readResource("/images/Game Over.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String readResource(String path) throws IOException {
        try (InputStream is = Introduction.class.getResourceAsStream(path)) {
            if (is == null) {
                throw new FileNotFoundException("Resource not found: " + path);
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}