package com.ipiecoles;

public class WeatherRequest {

    private String city;

    public WeatherRequest() {
        this.city = null;
    }

    public WeatherRequest(String city) {
        this.city = city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public boolean isProperlySet() {
        return getCity() != null && !getCity().isEmpty();
    }
}
