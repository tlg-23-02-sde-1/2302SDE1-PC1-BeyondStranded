package com.beyondstranded;

import java.util.List;

public class NPC {
    //fields
    private final String name;
    private final List<String> dialogue;
    private final String location;
    private final String type;
    private boolean hasHelped;
    private List<String> items;
    private final List<String> aidDialogue;

    //ctors
    public NPC(String name, List<String> dialogue, String location, String type, boolean hasHelped, List<String> items, List<String> aidDialogue) {
        this.name = name;
        this.dialogue = dialogue;
        this.location = location;
        this.type = type;
        this.hasHelped = hasHelped;
        this.items = items;
        this.aidDialogue = aidDialogue;
    }

    //accessor get/set/toString
    public List<String> getDialogue() {
        return dialogue;
    }

    public String getName() {
        return name;
    }

    public List<String> getAidDialogue() {
        return aidDialogue;
    }

    public String getLocation() {
        return location;
    }

    public boolean isHasHelped() {
        return hasHelped;
    }

    public void setHasHelped(boolean hasHelped) {
        this.hasHelped = hasHelped;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
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