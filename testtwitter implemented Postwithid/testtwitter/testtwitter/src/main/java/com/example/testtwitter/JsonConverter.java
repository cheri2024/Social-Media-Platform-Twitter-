package com.example.testtwitter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class JsonConverter {

    public static String convertToJson(String errorMessage) {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("Error", errorMessage);
        return convertMToJson(jsonMap);
    }

    private static String convertMToJson(Map<String, String> map) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            return mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String convertMapToJson(Map<String, String> responseBody) {
        ObjectMapper mapper = new ObjectMapper();

        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            return mapper.writeValueAsString(responseBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null; // or handle the exception as needed
        }
    }

    public static ResponseEntity<String> convertToJsonResponse(HttpStatus status, String message) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("Error", message);
        String jsonBody = convertMapToJson(responseBody);
        if (jsonBody != null) {
            return ResponseEntity.status(status).body(jsonBody);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error converting to JSON");
        }
    }


}
