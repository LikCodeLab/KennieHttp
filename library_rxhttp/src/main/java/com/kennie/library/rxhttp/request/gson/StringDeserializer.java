package com.kennie.library.rxhttp.request.gson;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * 描述：
 *
 */
public class StringDeserializer implements JsonDeserializer<String> {
    @Nullable
    @Override
    public String deserialize(@NonNull JsonElement json, @NonNull Type typeOfT, @NonNull JsonDeserializationContext context) throws JsonParseException {
        if (!json.isJsonObject()) {
            return "";
        }
        try {
            return json.getAsString();
        } catch (Exception ignore){
        }
        try {
            long l = json.getAsLong();
            return String.valueOf(l);
        } catch (Exception ignore){
        }
        try {
            double d = json.getAsDouble();
            return String.valueOf(d);
        } catch (Exception ignore){
        }
        return "";
    }
}
