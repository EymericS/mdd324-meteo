package com.ipiecoles;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Handler implements RequestHandler<WeatherRequest, GatewayResponse> {
    @Override
    public GatewayResponse handleRequest(WeatherRequest o, Context context) {
        WeatherService weatherService = new WeatherService();
        Weather weather = null;
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Access-Control-Allow-Origin", "https://pjvilloud.github.io");
        if (o == null || o.getVille() == null || o.getVille().isEmpty()) {
            return new GatewayResponse("{\"error\":\"Pas de ville\"}", headers, 400);
        }
        try {
            weather = weatherService.getWeatherOfTheCity(o.getVille());
        } catch (Exception exception) {
            // Gestion d'erreur
            System.out.println(exception.getMessage());
            if (exception.getMessage() == "Mauvaise clé API") {
                return new GatewayResponse("{\"error\":\"Mauvaise clé API\"}", headers, 401);
            }
            return new GatewayResponse("{\"error\":\"Problème lors de la récupération de la météo\"}", headers, 500);
        }
        String body = new Gson().toJson(weather);
        return new GatewayResponse(body, headers, 200);
    }
}
