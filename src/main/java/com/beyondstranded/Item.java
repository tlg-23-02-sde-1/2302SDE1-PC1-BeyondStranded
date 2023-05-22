package com.beyondstranded;

public class Item {
    //fields
    private String use;
    private String name;
    private String description;
    private String location;
    Types types;

    //ctors

    public Item(String use, String name, String description, String location, Types types) {
        this.use = use;
        this.name = name;
        this.description = description;
        this.location = location;
        this.types = types;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}