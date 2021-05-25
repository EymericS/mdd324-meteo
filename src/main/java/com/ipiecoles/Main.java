package com.ipiecoles;

public class Main {
    public static void main (String[] args){
        String city = "Paris";
        try {
            System.out.println(new WeatherService().getWeatherOfTheCity(city).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(Date.from(Instant.ofEpochSecond(1621971476)).getHours() + ":" + Date.from(Instant.ofEpochSecond(1621971476)).getMinutes());
    }
}
