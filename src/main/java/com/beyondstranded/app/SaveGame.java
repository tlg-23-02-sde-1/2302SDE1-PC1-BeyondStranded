package com.beyondstranded.app;

import com.beyondstranded.Inventory;
import com.beyondstranded.Item;
import com.beyondstranded.Location;
import com.beyondstranded.Player;
import com.google.gson.*;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SaveGame implements java.io.Serializable {
   Location location;
   Player player;
   Inventory inventory;




//    @Override
//    public Component deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//        return null;
//    }
//
//    @Override
//    public JsonElement serialize(Component src, Type typeOfSrc, JsonSerializationContext context) {
//        JsonObject result = new JsonObject();
//        result.add("type", new JsonPrimitive(src.getClass().getCanonicalName()));
//        result.add("properties", context.serialize(src, src.getClass()));
//        return result;
//    }
}