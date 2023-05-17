package com.beyondstranded.app;

import com.util.apps.Prompter;
import com.util.apps.SplashApp;

import java.util.Scanner;

public class Controller implements SplashApp {

//fields
    private final Scanner scanner = new Scanner(System.in);
    private final Prompter prompter = new Prompter(new Scanner(System.in));

//ctors
//method
    public void execute() {

    }

    @Override
    public void start() {
        startGame();
    }

    private void startGame() {

    }
    //business
    //accessor get/set/toString
    //helper
//inner class

}