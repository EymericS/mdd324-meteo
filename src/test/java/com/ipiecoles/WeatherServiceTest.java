package com.ipiecoles;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

    @Mock
    private WebUtils webUtils;
    @Test
    public void testGetWeatherOfTheCity() throws Exception{
        //Given
        Mockito.when(webUtils.getPageContents(Mockito.anyString())).thenReturn("{\"coord\":{\"lon\":2.3488,\"lat\":48.8534},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"couvert\",\"icon\":\"04d\"}],\"base\":\"stations\",\"main\":{\"temp\":15.22,\"feels_like\":14.31,\"temp_min\":14.1,\"temp_max\":16.23,\"pressure\":1018,\"humidity\":58},\"visibility\":10000,\"wind\":{\"speed\":2.24,\"deg\":220,\"gust\":7.6},\"clouds\":{\"all\":90},\"dt\":1621956068,\"sys\":{\"type\":2,\"id\":2012208,\"country\":\"FR\",\"sunrise\":1621915050,\"sunset\":1621971476},\"timezone\":7200,\"id\":2988507,\"name\":\"Paris\",\"cod\":200}");
        //When
        Weather weather = new WeatherService(webUtils).getWeatherOfTheCity("Paris");
        //Then
        Assertions.assertThat(weather).isNotNull();
        Assertions.assertThat(weather.getCoucher()).isEqualTo("21:37");
        Assertions.assertThat(weather.getLever()).isEqualTo("5:57");
        Assertions.assertThat(weather.getTemps()).isEqualTo("couvert");
        Assertions.assertThat(weather.getHumidite()).isEqualTo(58);
        Assertions.assertThat(weather.getTemp()).isEqualTo(15.22);
        Assertions.assertThat(weather.getIcon()).isEqualTo(804);
    }

    @Test
    public void testGetWeatherOfTheCityError401() throws Exception{
        //Given
        Mockito.when(webUtils.getPageContents(Mockito.anyString())).thenReturn("{\"cod\":401, \"message\": \"Invalid API key. Please see http://openweathermap.org/faq#error401 for more info.\"}");
        //When
        try {
            Weather weather = new WeatherService(webUtils).getWeatherOfTheCity("Paris");
        } catch (Exception e) {
            Assertions.assertThat(e).isInstanceOf(Exception.class);
            Assertions.assertThat(e.getMessage()).isEqualTo("Mauvaise clé API");
        }
    }

    @Test
    /**
     * Case : city not found
     */
    public void testGetWeatherOfTheCityError404() throws Exception{
        //Given
        Mockito.when(webUtils.getPageContents(Mockito.anyString())).thenReturn("{\"cod\":404,\"message\":\"city not found\"}");
        //When
        try {
            Weather weather = new WeatherService(webUtils).getWeatherOfTheCity("Paris");
        } catch (Exception e) {
            Assertions.assertThat(e).isInstanceOf(Exception.class);
            Assertions.assertThat(e.getMessage()).isEqualTo("Ville pas trouvé");
        }
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void testGetWeatherOfTheCityError400() throws Exception{
        //Given
        Mockito.when(webUtils.getPageContents(Mockito.anyString())).thenReturn(null);
        //When
        try {
            Weather weather = new WeatherService(webUtils).getWeatherOfTheCity(null);
        } catch (Exception e) {
            Assertions.assertThat(e).isInstanceOf(Exception.class);
            Assertions.assertThat(e.getMessage()).isEqualTo("Pas de ville");
        }
    }

    @Test
    public void testGetWeatherOfTheCityErrorContenuNull() throws Exception{
        //Given
        Mockito.when(webUtils.getPageContents(Mockito.anyString())).thenReturn(null);
        //When
        try {
            Weather weather = new WeatherService(webUtils).getWeatherOfTheCity("Paris");
        } catch (Exception e) {
            Assertions.assertThat(e).isInstanceOf(Exception.class);
            Assertions.assertThat(e.getMessage()).isEqualTo("Contenu vide");
        }
    }
}