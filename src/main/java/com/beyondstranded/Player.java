package com.beyondstranded;

public class Player {

    String name;
    Location location;
    int health;
    Inventory inventory;

    public Player(String name, Location location, int health, Inventory inventory) {
        this.name = name;
        this.location = location;
        this.health = health;
        this.inventory = inventory;
    }

    public Location moveTo(Location currentLocation) {
        Location location = new Location();
        return location;
    }

    public Inventory getItems(Location currentLocation, Inventory currentInventory, String input) {
        Inventory inventory = new Inventory();
        return inventory;
    }



    //fields
//ctors
//method
    //business
    //accessor get/set/toString
    //helper
//inner class

}