package com.beyondstranded;

import com.google.gson.annotations.SerializedName;

public class Item {
    //fields
    private String use;
    private String name;
    private String description;
    private String location;
    @SerializedName("type")
    Type types;

    //ctors

    public Item(String use, String name) {
        this.use = use;
        this.name = name;
//        this.description = description;
//        this.location = location;
//        this.types = types;
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

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", use='" + use + '\'' +
                '}';
    }
}