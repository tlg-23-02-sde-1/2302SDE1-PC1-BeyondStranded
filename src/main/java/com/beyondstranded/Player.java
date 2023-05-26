package com.beyondstranded;

import java.util.List;
import java.util.Map;

public class Player {

//    String name;
    Location location;
    int health;
    List<String> inventory;
    Map<String, Location> visitedLocations;

/*    public Player(String name, Location location, int health, Inventory inventory) {
        this.name = name;
        this.location = location;
        this.health = health;
        this.inventory = inventory;
    }*/

    public Player(Location location, int health, List<String> inventory, Map<String, Location> visitedLocations) {
        this.location = location;
        this.health = health;
        this.inventory = inventory;
        this.visitedLocations = visitedLocations;
    }

    public Player() {

    }

/*    public Location moveTo(Location currentLocation) {
        Location location = new Location(currentLocation);
        return location;
    }*/

/*    public Inventory getItems(Location currentLocation, Inventory currentInventory, String input) {
        Inventory inventory = new Inventory();
        return inventory;
    }*/

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void getItem() {
    }

    public Map<String, Location> getVisitedLocations() {
        return visitedLocations;
    }

    public void setVisitedLocations(Map<String, Location> visitedLocations) {
        this.visitedLocations = visitedLocations;
    }

    public List<String> getInventory() {
        return inventory;
    }

    public void addItemToInventory(String item){
        getInventory().add(item);
    }

    public void removeItemFromInventory(String item){
        getInventory().remove(item);
    }

    public void setInventory(List<String> inventory) {
        this.inventory = inventory;
    }
}