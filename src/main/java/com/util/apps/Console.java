//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.util.apps;

import java.io.IOException;

public class Console {
    private static final String os = System.getProperty("os.name").toLowerCase();

    private Console() {
    }

    public static void blankLines(int var0) {
        for (int var1 = 0; var1 < var0; ++var1) {
            System.out.println();
        }
    }

    public static void clear() {
        ProcessBuilder var0;
        if(os.contains("windows")) {
            var0 = new ProcessBuilder("cmd", "/c", "cls");
        } else if(os.contains("mac")) {
            var0 = new ProcessBuilder("/bin/bash", "-c", "printf '\\033c'");
        }
        else {
            var0 = new ProcessBuilder("clear");
        }

        try {
            var0.inheritIO().start().waitFor();
        } catch (InterruptedException var2) {
            var2.printStackTrace();
        } catch (IOException var3) {
            var3.printStackTrace();
        }
    }

    public static void pause(long var0) {
        try {
            Thread.sleep(var0);
        } catch (InterruptedException var3) {
            var3.printStackTrace();
        }
    }
}