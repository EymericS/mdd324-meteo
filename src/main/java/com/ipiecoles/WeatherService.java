package com.ipiecoles;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;

public class WeatherService {

    private final String apiEndPoint = "https://api.openweathermap.org/data/2.5/weather";
    private final String apiKey = "5dfc2a06c8157403e9107053a73aca92";
    private final String lang = "fr";
    private final String units = "Metric";
    private WebUtils webUtils;

    public WeatherService() {
        this.webUtils = new WebUtils();
    }

    public WeatherService(WebUtils webUtils) {
        this.webUtils = webUtils;
    }

    /**
     * Create and make call to external api and return content
     * @param city String
     * @return content JsonObject
     */
    private JsonObject callExternalAPI(String city) throws Exception {
        final String url = apiEndPoint + '?' +
                "appid=" + apiKey +
                "&lang="  + lang +
                "&units=" + units +
                "&q=" + city;

        String pageContent = null;
        pageContent = webUtils.getPageContents(url);
        JsonObject result = new JsonParser().parse(pageContent).getAsJsonObject();

        return result;
    }

    /**
     * Create a weather instance from external API ressource
     * @param city String
     * @return Weather
     */
    public Weather getWeatherOfTheCity(String city) throws WeatherException {
        if(city == null || city.isEmpty()) throw new WeatherException(WeatherException.TYPE.NO_CITY, 400);

        JsonObject responseBody = null;
        try{
            responseBody = callExternalAPI(city);
        } catch (Exception exception){
            throw new WeatherException(WeatherException.TYPE.CITY_NOT_FOUND, 404);
        }

        if(responseBody == null) throw new WeatherException(WeatherException.TYPE.UNKNOWN_EXTERNAL_ERROR);

        int httpResponseCode = responseBody.get("cod").getAsInt();
        if (httpResponseCode != 200){
            switch(httpResponseCode) {
                case 400: throw new WeatherException(WeatherException.TYPE.NO_CITY, httpResponseCode);
                case 401: throw new WeatherException(WeatherException.TYPE.WRONG_EXTERNAL_API_KEY, httpResponseCode);
                case 404: throw new WeatherException(WeatherException.TYPE.CITY_NOT_FOUND, httpResponseCode);
                default: throw new WeatherException(WeatherException.TYPE.UNHANDLE_HTTP_CODE);
            }
        }

        return createWeatherFromStructuredJSON(responseBody);
    }

    private Weather createWeatherFromStructuredJSON(JsonObject json) {
        JsonObject JOWeather = json.getAsJsonArray("weather").get(0).getAsJsonObject();
        JsonObject JOSys = json.get("sys").getAsJsonObject();
        JsonObject JOMain = json.get("main").getAsJsonObject();

        // sunset / sunrise time
        long timestamp = JOSys.get("sunset").getAsLong();
        SimpleDateFormat formater = new SimpleDateFormat("HH:mm");
        Date date = new Date(timestamp*1000);
        String sunset = formater.format(date);

        timestamp = JOSys.get("sunrise").getAsLong();
        date = new Date(timestamp*1000);
        String sunrise = formater.format(date);

        // humidite
        int humidity = JOMain.get("humidity").getAsInt();

        // icon
        int icon = JOWeather.get("id").getAsInt();

        // temp
        Double temp = JOMain.get("temp").getAsDouble();

        // temps
        String temps = JOWeather.get("description").getAsString();

        return new Weather(sunset, humidity, icon, sunrise, temp, temps);
    }
}