package com.beyondstranded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class GameMap {
    private final char[][] gameMap;
    private String currentPlayerLocation = null;
    private String previousPlayerLocation = null;
    private int[] previousPlayerPosition = null;
    private String currentLocation = null;
    private String previousLocation = null;


    public GameMap() {
        // Initialize the map as a 2D array filled with '.'
        this.gameMap = new char[48][105];
        for (char[] row : this.gameMap) {
            Arrays.fill(row, '.');
        }
    }

    public void startMap() {
        try {
            visitLocation("Compass", 85, 0);
            //visitLocation("Awakening", 30, 15);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentPlayerLocation(String location) {
        this.currentPlayerLocation = location;
    }

    public void setPreviousPlayerPosition(int x, int y) {
        this.previousPlayerPosition = new int[]{x, y};
    }

    public void resetPreviousLocation() throws IOException {
        if (currentPlayerLocation != null && previousPlayerPosition != null) {
            visitLocation(currentPlayerLocation, previousPlayerPosition[0], previousPlayerPosition[1]);
        }
    }

    public void markPlayerPosition(String location, int x, int y) {
        gameMap[y][x] = location.equals(currentLocation) ? 'P' : '.';
    }

    public void unmarkPlayerPosition(int x, int y) {
        gameMap[y][x] = '.';
    }

    public void visitLocation(String location, int x, int y) throws IOException {
        char[][] locationArt;

        switch (location) {
            case "Awakening":
                locationArt = readLocationFromFile("/ASCII_Art/awakening.txt");
                break;
            case "Cave":
                locationArt = readLocationFromFile("/ASCII_Art/cave.txt");
                break;
            case "Forest Clearing":
                locationArt = readLocationFromFile("/ASCII_Art/forest.txt");
                break;
            case "Waterfall":
                locationArt = readLocationFromFile("/ASCII_Art/waterfall.txt");
                break;
            case "Village":
                locationArt = readLocationFromFile("/ASCII_Art/village.txt");
                break;
            case "Rocky Shore":
                locationArt = readLocationFromFile("/ASCII_Art/rocky shore.txt");
                break;
            case "Cliff":
                locationArt = readLocationFromFile("/ASCII_Art/cliff.txt");
                break;
            case "Compass":
                locationArt = readLocationFromFile("/ASCII_Art/compass.txt");
                break;
            case "Helicopter Crash":
                locationArt = readLocationFromFile("/ASCII_Art/helicopter.txt");
                break;
            case "Cove":
                locationArt = readLocationFromFile("/ASCII_Art/cove.txt");
                break;
            case "Ship Wreck":
                locationArt = readLocationFromFile("/ASCII_Art/shipwreck.txt");
                break;
            case "Dense Jungle":
                locationArt = readLocationFromFile("/ASCII_Art/dense jungle.txt");
                break;
            case "Volcano":
                locationArt = readLocationFromFile("/ASCII_Art/volcano.txt");
                break;
            default:
                throw new IllegalArgumentException("Unknown location: " + location);
        }

        currentLocation = location;

        for (int i = 0; i < locationArt.length; i++) {
            for (int j = 0; j < locationArt[i].length; j++) {
                this.gameMap[y + i][x + j] = locationArt[i][j];
            }
        }
        if (!location.equals("Compass")) {
            markPlayerPosition(location, x, y);
        }
    }

    public void printMap() {
        for (char[] row : this.gameMap) {
            int lastIndex = row.length - 1;
            while (lastIndex >= 0 && row[lastIndex] == '.') {
                lastIndex--;
            }
            System.out.println(new String(row, 0, lastIndex + 1));
        }
    }

    public static Map<String, List<Integer>> readCsv(String filepath) {
        Map<String, List<Integer>> locations = new HashMap<>();
        //noinspection ConstantConditions
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(GameMap.class.getResourceAsStream(filepath)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String locationName = parts[0];
                int x = Integer.parseInt(parts[1]);
                int y = Integer.parseInt(parts[2]);
                locations.put(locationName, Arrays.asList(x, y));
            }
        } catch (IOException e) {
            System.out.println("There was an error reading the file: " + e.getMessage());
        }
        return locations;
    }

    private static char[][] readLocationFromFile(String filePath) throws IOException {
        //noinspection ConstantConditions
        try (BufferedReader br = new BufferedReader(new InputStreamReader(GameMap.class.getResourceAsStream(filePath), StandardCharsets.UTF_8))) {
            List<String> lines = new ArrayList<>();
            String line;
            int width = 0;

            while ((line = br.readLine()) != null) {
                lines.add(line);
                if (line.length() > width) {
                    width = line.length(); // update the width if the current line is longer
                }
            }

            int height = lines.size();
            char[][] locationArt = new char[height][width];

            for (int i = 0; i < height; i++) {
                line = lines.get(i);
                for (int j = 0; j < line.length(); j++) {
                    locationArt[i][j] = line.charAt(j);
                }
            }
            return locationArt;
        }
    }
}
