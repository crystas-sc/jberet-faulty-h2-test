package org.capps.testutil;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;


public class JSONFunctions {
    static ObjectMapper objectMapper = new ObjectMapper();

    public static String jsonBuildObject(String... keysAndValues) throws JsonProcessingException {
        if (keysAndValues.length % 2 != 0) {
            throw new IllegalArgumentException("Keys and values must come in pairs");
        }
        Map<String, Object> jsonMap = new LinkedHashMap<>();
        for (int i = 0; i < keysAndValues.length; i += 2) {
            try {
                String nextValue = keysAndValues[i + 1];
                if (nextValue!= null) {
                    jsonMap.put(keysAndValues[i], objectMapper.readTree(nextValue));
                } else {
                    jsonMap.put(keysAndValues[i], null);
                }
            } catch (JsonParseException e) {
                String str = keysAndValues[i + 1];
                if (isNumeric(str))
                    jsonMap.put(keysAndValues[i], Double.parseDouble(str));
                else
                    jsonMap.put(keysAndValues[i], str);
            }
        }
        return objectMapper.writeValueAsString(jsonMap);
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String jsonbSet(String json, String key, String newValue) throws JsonProcessingException {
//        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        String keys[] = key.replaceAll("^\\{|\\}$", "").split(",");
        JsonNode rootNode = objectMapper.readValue(json, JsonNode.class);
        if (rootNode instanceof TextNode) {
            rootNode = objectMapper.readTree(rootNode.asText());
        }
        // Traverse the JSON tree using the provided path
        JsonNode currentNode = rootNode;
        for (String k : Arrays.copyOfRange(keys, 0, keys.length - 1)) {
            if (currentNode.has(k)) {
                currentNode = currentNode.get(k);
            } else {
                return null; // Return null if any key is not found in the path
            }
        }

        if (newValue.substring(0, 2).equals("\"{")) {
            ((ObjectNode) rootNode).put(keys[keys.length - 1], objectMapper.readTree(objectMapper.readTree(newValue).asText()));
        } else
            ((ObjectNode) rootNode).put(keys[keys.length - 1], newValue);
//        Map<String, Object> jsonMap = objectMapper.readValue(json, Map.class);
//        jsonMap.put(key, newValue);

        return objectMapper.writeValueAsString(rootNode);
    }

    public static String jsonArrowOperator(String json, String key) throws JsonProcessingException {
        Map<String, Object> jsonMap = objectMapper.readValue(json, Map.class);
        return jsonMap.get(key) != null ? jsonMap.get(key).toString() : null;
    }

    public static String jsonbExtractPath(String json, String... path) {
        if (json == null || path == null || path.length == 0) {
            return null;
        }

        try {
            // Parse the JSON string into a JsonNode (root node of the tree)
            JsonNode rootNode = objectMapper.readValue(json, JsonNode.class);
            if (rootNode instanceof TextNode) {
                rootNode = objectMapper.readTree(rootNode.asText());
            }

            // Traverse the JSON tree using the provided path
            JsonNode currentNode = rootNode;
            for (String key : path) {
                if (currentNode.has(key)) {
                    currentNode = currentNode.get(key);
                } else {
                    return null; // Return null if any key is not found in the path
                }
            }

            // Return the value found at the end of the path as a JSON string
            return objectMapper.writeValueAsString(currentNode);

        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null if there's an error with parsing
        }
    }


}

