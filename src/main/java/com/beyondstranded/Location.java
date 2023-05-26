package com.beyondstranded;

import java.util.List;
import java.util.Map;

public class Location {
    private String name;
    private Map<String, String> directions;
    private String description;
    private List<String> items;
    private List<String> npc;
    private boolean hasVisited;

    public Location(String name, Map<String, String> directions, String description, List<String> items, List<String> npc, boolean hasVisited) {
        this.name = name;
        this.directions = directions;
        this.description = description;
        this.items = items;
        this.npc = npc;
        this.hasVisited = hasVisited;
    }

    public Location(String current_location) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getDirections() {
        return directions;
    }

    public void setDirections(Map<String, String> directions) {
        this.directions = directions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getNpc() {
        return npc;
    }

    public void setNpc(List<String> npc) {
        this.npc = npc;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public boolean isHasVisited() {
        return hasVisited;
    }

    public void setHasVisited(boolean hasVisited) {
        this.hasVisited = hasVisited;
    }

    //    public Item getItemByName(String itemName) {
//        for (Item item : items){
//            if(item.getName().equalsIgnoreCase(itemName)){
//                return item;
//            }
//        }
//        // If item not found
//        return null;
//    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", directions=" + directions +
                ", description='" + description + '\'' +
                ", items=" + items +
                ", npc=" + npc +
                ", hasVisited=" + hasVisited +
                '}';
    }
}