package com.beyondstranded;

import com.google.gson.annotations.SerializedName;

enum Types {
    @SerializedName("Puzzle")
    PUZZLE,
    @SerializedName("Healing")
    HEALING,
    @SerializedName("Food")
    FOOD
}