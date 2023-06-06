package com.flexone.catchwise.bootstrap.dto;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class StateJSONDeserializer extends StdDeserializer<StateJSON> {

    public StateJSONDeserializer(Class<?> vc) {
        super(vc);
    }

    public StateJSONDeserializer(JavaType valueType) {
        super(valueType);
    }

    public StateJSONDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    @Override
    public StateJSON deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        return null;
    }
}
