package com.beyondstranded;

public class NPC {
    //fields
    private String name;
    private String description;
    private String location;
    private String type;

    //ctors
    public NPC(String name, String description, String location, String type) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.type = type;
    }

    //accessor get/set/toString
    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "NPC{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

//method
    //business
    //helper
//inner class

}