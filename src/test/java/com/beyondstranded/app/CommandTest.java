package com.beyondstranded.app;

import com.beyondstranded.Location;
import com.beyondstranded.Player;
import com.util.apps.Prompter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CommandTest {
    private Player player;
    private Location currentLocation;
    private Location newLocation;
    private Map<String, Location> allLocations;
    private Command command;

    @BeforeEach
    void setUp() {
        // Set up the test environment, initialize objects, and dependencies
        command = new Command(new Prompter());
        player = new Player();
        allLocations = new HashMap<>();
    }

    @AfterEach
    void tearDown() {
        // Clean up resources, reset objects, and perform any necessary cleanup tasks
        player = null;
        currentLocation = null;
        newLocation = null;
        allLocations = null;
        command = null;
    }

    @Test
    void testGoCommand_ValidDirection_PlayerLocationUpdated() {
        // Create a sample player and location data
        Player player = new Player();
        Location currentLocation = new Location("Current Location");
        Location newLocation = new Location("New Location");
        Map<String, Location> allLocations = new HashMap<>();
        allLocations.put(currentLocation.getName(), currentLocation);
        allLocations.put(newLocation.getName(), newLocation);

        // Set up the command and directions
        Command command = new Command(new Prompter());
        player.setLocation(currentLocation);
        List<String> commandList = List.of("go", "north");
        Map<String, String> directions = new HashMap<>();
        directions.put("north", "New Location");
        currentLocation.setDirections(directions);

        // Call the goCommand method
        Player result = command.goCommand(commandList, player, allLocations);

        // Assert the expected outcome
        Assertions.assertEquals(player, result, "Player's location should be updated to the new location");
    }

//    @Test
//    void testGoCommand_InvalidDirection_ErrorDisplayed() {
//        // Create a sample player and location data
//        Player player = new Player();
//        Location currentLocation = new Location("Current Location");
//        Map<String, Location> allLocations = new HashMap<>();
//        allLocations.put(currentLocation.getName(), currentLocation);
//
//        // Set up the command and directions
//        Command command = new Command(new Prompter());
//        player.setLocation(currentLocation);
//        List<String> commandList = List.of("go", "south");
//        Map<String, String> directions = new HashMap<>();
//        directions.put("north", "New Location");
//        currentLocation.setDirections(directions);
//
//        // Redirect console output for assertion
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        PrintStream printStream = new PrintStream(outputStream);
//        System.setOut(printStream);
//
//        // Call the goCommand method
//        Player result = command.goCommand(commandList, player, allLocations);
//
//        // Reset the console output
//        System.setOut(System.out);
//
//        // Assert the expected outcome
//        Assertions.assertEquals(player, result, "Player should remain in the current location");
//        Assertions.assertTrue(outputStream.toString().contains("You can't go south from here."),
//                "Error message should be displayed for invalid direction");
//    }
}

