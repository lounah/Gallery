package com.lounah.gallery.util;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.lounah.gallery.data.entity.Photo;

import java.lang.reflect.Type;
import java.util.List;

public class GalleryJsonDeserializer implements JsonDeserializer<List<Photo>> {

    private static final String ITEMS = "items";

    @Override
    public List<Photo> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new GsonBuilder()
                .create()
                .fromJson(json.getAsJsonObject().get(ITEMS).getAsJsonArray(), new TypeToken<List<Photo>>(){}.getType());
    }
}
