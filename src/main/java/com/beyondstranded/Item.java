package com.beyondstranded;

public class Item {
    String name;
    String use;


    public Item(String name, String use) {
        this.name = name;
        this.use = use;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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