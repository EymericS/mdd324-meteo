package com.ipiecoles;

public class Weather {
    private String sunsetDate;
    private Integer humidity;
    private Integer id;
    private String sunriseDate;
    private Double temp;
    private String description;

    @Override
    public String toString() {
        return "Weather{" +
                "sunsetDate='" + sunsetDate + '\'' +
                ", humidity=" + humidity +
                ", id=" + id +
                ", sunriseDate='" + sunriseDate + '\'' +
                ", temp=" + temp +
                ", description='" + description + '\'' +
                '}';
    }

    public Weather(String sunsetDate, Integer humidity, Integer id, String sunriseDate, Double temp, String description) {
        this.sunsetDate = sunsetDate;
        this.humidity = humidity;
        this.id = id;
        this.sunriseDate = sunriseDate;
        this.temp = temp;
        this.description = description;
    }

    public String getSunsetDate() {
        return sunsetDate;
    }

    public void setSunsetDate(String sunsetDate) {
        this.sunsetDate = sunsetDate;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getsunriseDate() {
        return sunriseDate;
    }

    public void setsunriseDate(String sunriseDate) {
        this.sunriseDate = sunriseDate;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
