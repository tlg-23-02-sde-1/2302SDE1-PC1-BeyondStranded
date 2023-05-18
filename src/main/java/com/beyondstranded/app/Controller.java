package com.beyondstranded.app;

import com.beyondstranded.Player;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.util.apps.Prompter;
import com.util.apps.SplashApp;

import java.nio.file.Files;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.Scanner;

public class Controller implements SplashApp {

//fields
    private final Prompter prompter = new Prompter(new Scanner(System.in));
    private static Gson gson = new Gson();
    private static Player player;
    private Parser parser;

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

    //business
    //accessor get/set/toString
    //helper
//inner class

}