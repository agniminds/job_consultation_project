package com.test.servlet.utility;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LocalDateTimeSerializer implements JsonSerializer <Date> {


    @Override
    public JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext) {

        SimpleDateFormat date1 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        return new JsonPrimitive(date1.format(date));

    }
}
