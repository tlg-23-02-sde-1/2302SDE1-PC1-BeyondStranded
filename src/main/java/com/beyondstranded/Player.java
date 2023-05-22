package com.beyondstranded;

import java.util.List;

public class Player {

//    String name;
    Location location;
    int health;
    List<Item> inventory;

/*    public Player(String name, Location location, int health, Inventory inventory) {
        this.name = name;
        this.location = location;
        this.health = health;
        this.inventory = inventory;
    }*/

    public Player(Location location, int health, List<Item> inventory) {
        this.location = location;
        this.health = health;
        this.inventory = inventory;
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

    public List<Item> getInventory() {
        return inventory;
    }

    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }

    //fields
//ctors
//method
    //business
    //accessor get/set/toString
    //helper
//inner class

}