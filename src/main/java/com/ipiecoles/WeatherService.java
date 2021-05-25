package com.ipiecoles;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class WeatherService {

    private final String apiEndPoint = "https://api.openweathermap.org/data/2.5/weather";
    private final String apiKey = "5dfc2a06c8157403e9107053a73aca92";
    private final String lang = "fr";
    private final String units = "Metric";
    private String city = null;
    private String contenu = null;

    private WebUtils webUtils = new WebUtils();
    public WeatherService() {}

    public WeatherService(WebUtils webUtils) {
        this.webUtils = webUtils;
    }

    public void callExternalAPI(){
        final String urlApi = apiEndPoint + '?' +
                "appid=" + apiKey +
                "&lang="  + lang +
                "&units=" + units +
                "&q=" + city;
        try {
            contenu = webUtils.getPageContents(urlApi);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode allant chercher la météo dans une ville donnée
     *
     * @return la météo actuelle de la ville donnée sous forme d'une chaîne de caractère
     * représentant un objet JSON :
     * {
     *     "coucher":"15:53",
     *     "humidite":70,
     *     "icon":803,
     *     "lever":"07:31",
     *     "temp":8.13,
     *     "temps":"nuageux"
     * }
     */
    public Weather getWeatherOfTheCity(String city) throws Exception {

        //Appel à l'API
        this.city = city;
        callExternalAPI();

        if (contenu == null){
            throw new Exception("Contenu vide");
        }

        //Je gère les erreurs


        //Récuperation des valeurs
        JsonObject JsonObject = new JsonParser().parse(contenu).getAsJsonObject();
        JsonArray weather = JsonObject.getAsJsonArray("weather");
        JsonObject sys = JsonObject.get("sys").getAsJsonObject();
        JsonObject main = JsonObject.get("main").getAsJsonObject();

        String description = weather.get(0).getAsJsonObject().get("description").getAsString();
        Long sunset = sys.get("sunset").getAsLong();
        String sunsetDate = Date.from(Instant.ofEpochSecond(sunset)).getHours() + ":" + Date.from(Instant.ofEpochSecond(sunset)).getMinutes();
        Integer humidity = main.get("humidity").getAsInt();
        Integer id = weather.get(0).getAsJsonObject().get("id").getAsInt();
        Long sunrise = sys.get("sunrise").getAsLong();
        String sunriseDate = Date.from(Instant.ofEpochSecond(sunrise)).getHours() + ":" + Date.from(Instant.ofEpochSecond(sunrise)).getMinutes();
        Double temp = main.get("temp").getAsDouble();

        return new Weather(sunsetDate, humidity, id, sunriseDate, temp, description);
    }
}