package com.beyondstranded;

import java.util.List;

public class NPC {
    //fields
    private final String name;
    private final List<String> dialogue;
    private final String location;
    private final String type;
    private int health;

    //ctors
    public NPC(String name, List<String> dialogue, String location, String type) {
        this.name = name;
        this.dialogue = dialogue;
        this.location = location;
        this.type = type;
    }



    //accessor get/set/toString
    public List<String> getDialogue() {
        return dialogue;
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
                ", dialogue='" + dialogue + '\'' +
                ", location='" + location + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}