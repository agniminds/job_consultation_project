package com.test.servlet.utility;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LocalDateTimeDeserializer implements JsonDeserializer <Date>  {


    @Override
    public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        System.out.println("- --- in Deserializer---");
        System.out.println(jsonElement.getAsString());
        SimpleDateFormat date1 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

        try {
            return date1.parse(jsonElement.getAsString());
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }



    /*@Override
    public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context) {
        return new JsonPrimitive(formatter.format(localDateTime));
    }*/

    /*public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        System.out.println(json.getAsString());
        return LocalDateTime.parse(json.getAsString(),
                DateTimeFormatter.ofPattern("dd-M-yyyy hh:mm:ss").withLocale(Locale.ENGLISH));
    }*/
}