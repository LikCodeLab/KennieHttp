package com.kennie.library.http.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class CharTypeAdapter extends TypeAdapter<Character> {

    @Override
    public void write(JsonWriter out, Character value) throws IOException {
        out.value(value);
    }

    @Override
    public Character read(JsonReader in) throws IOException {
        if (in.peek().equals(JsonToken.NULL) || !in.peek().equals(JsonToken.NUMBER)) {
            in.skipValue();
            return '0';
        }

        try {
            return (char) in.nextInt();
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
            return '0';
        }
    }
}
