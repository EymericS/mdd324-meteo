package com.ipiecoles;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Handler implements RequestHandler<GatewayRequest, GatewayResponse> {

    /**
     *  Handle AWS API request on this lambda app
     * @param request GatewayRequest
     * @param context Context
     * @return GatewayResponse
     */
    @Override
    public GatewayResponse handleRequest(GatewayRequest request, Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Access-Control-Allow-Origin", "https://pjvilloud.github.io");

        WeatherRequest weatherRequest = null;
        try {
            weatherRequest = new Gson().fromJson(request.getBody(), WeatherRequest.class);
        } catch (JsonSyntaxException e) {
            return new GatewayResponse("{\"Gson exception\":\"" + e.getMessage() + "\"}", headers, 500);
        }

        if (weatherRequest == null || !weatherRequest.isProperlySet())
            return new GatewayResponse("{\"error\":\"Missing data\"}", headers, 400);

        WeatherService weatherService = new WeatherService();
        Weather weather = null;
        try {
            weather = weatherService.getWeatherOfTheCity(weatherRequest.getCity());
        } catch (WeatherException exception) {
            return new GatewayResponse(
                    "{\"error WeatherException\":\"" + exception.getMessage() +"\"}",
                    headers,
                    exception.get_statusCode());
        }

        return new GatewayResponse(new Gson().toJson(weather), headers, 200);
    }

}
