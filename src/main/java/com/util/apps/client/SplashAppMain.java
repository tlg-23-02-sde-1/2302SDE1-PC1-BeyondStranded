//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.util.apps.client;

import com.util.apps.SplashApp;

public class SplashAppMain implements SplashApp {
    public SplashAppMain() {
    }

    public void start() {
        System.out.println("\nW E L C O M E   T O   T H E   T E S T   A P P L I C A T I O N\n");
    }

    public static void main(String[] var0) {
        SplashAppMain var1 = new SplashAppMain();
        var1.welcome(2000L, new String[]{"images/credits.jpg", "images/java.png"});
        var1.start();
    }
}