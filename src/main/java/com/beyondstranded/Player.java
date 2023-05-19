package com.beyondstranded;

public class Player {

//    String name;
    Location location;
//    int health;
//    Inventory inventory;

/*    public Player(String name, Location location, int health, Inventory inventory) {
        this.name = name;
        this.location = location;
        this.health = health;
        this.inventory = inventory;
    }*/

    public Player(Location location) {
        this.location = location;
    }

/*    public Location moveTo(Location currentLocation) {
        Location location = new Location(currentLocation);
        return location;
    }*/

    public Inventory getItems(Location currentLocation, Inventory currentInventory, String input) {
        Inventory inventory = new Inventory();
        return inventory;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

//fields
//ctors
//method
    //business
    //accessor get/set/toString
    //helper
//inner class

}