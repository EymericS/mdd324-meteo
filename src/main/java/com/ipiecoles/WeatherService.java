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
    private final String apiKey = "5dfc2a06c8157403e9107053a73aca92t";
    private final String lang = "fr";
    private final String units = "Metric";
    private String city = null;
    private String contenu = null;

    private WebUtils webUtils = new WebUtils();
    public WeatherService() {}

    public WeatherService(WebUtils webUtils) {
        this.webUtils = webUtils;
    }

    public void callExternalAPI() throws Exception {
        // Quelle exception ?
        final String urlApi = apiEndPoint + '?' +
                "appid=" + apiKey +
                "&lang="  + lang +
                "&units=" + units +
                "&q=" + city;

        contenu = webUtils.getPageContents(urlApi);
        System.out.println(contenu);
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
        if (city == null || city.isEmpty()) {
            throw new Exception("Pas de ville");
        }
        this.city = city;
        try {
            System.out.println("beforeCall");
            callExternalAPI();
            System.out.println("afterCall");
        } catch (Exception exception) {
            System.out.println("message" + exception.getMessage());
            String codeErreur = exception.getMessage().substring(36,39);
            if (codeErreur.equals("401")) {
                throw new Exception("Mauvaise clé API");
            }

            if (codeErreur.equals("404")) {
                throw new Exception("Ville pas trouvé");
            }
        }

        System.out.println("test");

        if (contenu == null){
            throw new Exception("Contenu vide");
        }

        //Récuperation des valeurs
        JsonObject JsonObject = new JsonParser().parse(contenu).getAsJsonObject();
        int httpResponseCode = JsonObject.get("cod").getAsJsonObject().getAsInt();
        if (httpResponseCode != 200)
            throw new Exception("Response code != 200 !");

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