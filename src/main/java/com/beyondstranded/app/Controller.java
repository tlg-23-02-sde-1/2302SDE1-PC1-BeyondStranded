package com.beyondstranded.app;

import com.beyondstranded.Player;
import com.google.gson.Gson;
import com.util.apps.Prompter;
import com.util.apps.SplashApp;

import java.util.Scanner;

public class Controller implements SplashApp {

//fields
    private final Prompter prompter = new Prompter(new Scanner(System.in));
    private static Gson gson = new Gson();
    private static Player player;

//ctors


//method
    public void execute() {

    }

    @Override
    public void start() {
        startGame();

    }

    private void startGame() {
        Introduction intro = new Introduction(prompter);
        intro.showTitlePage();
        intro.gameOption();
        intro.showCoreStory();
    }

    private void handleUserInput(Introduction intro){
        boolean quit = false;
        while(!quit){
            intro.gameOption();
            String userInput = prompter.prompt("\n\t\t\t\t\tEnter your command: ", "(?i)^(quit|[a-z]+)$", "\t\t\t\t\tThis is not a valid option\n");
            if (Parser.runCommand(userInput)){
                quit = true;
            } else {
//                handleMovesInput(intro);
            }
        }
    }
    //business
    //accessor get/set/toString
    //helper
//inner class

}