package ru.yandex.practicum.dto;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class CustomMapDeserializer extends StdDeserializer<Map<UUID, Integer>> {

    protected CustomMapDeserializer() {
        this(null);
    }

    protected CustomMapDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Map<UUID, Integer> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        Iterator<Map.Entry<String, JsonNode>> fieldsIterator = node.fields();
        Map<UUID, Integer> map = new HashMap<>();

        while (fieldsIterator.hasNext()) {
            Map.Entry<String, JsonNode> entry = fieldsIterator.next();
            try {
                UUID uuidKey = UUID.fromString(entry.getKey());
                Integer value = entry.getValue().asInt();
                map.put(uuidKey, value);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Неверный формат ключа UUID.", e);
            }
        }
        return map;
    }
}