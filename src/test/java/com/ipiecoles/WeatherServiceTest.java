package com.ipiecoles;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

    @Mock
    private WebUtils webUtils;
    @Test
    public void testGetWeatherOfTheCity() throws Exception{
        //Given
        Mockito.when(webUtils.getPageContents(Mockito.anyString())).thenReturn("{\"coord\":{\"lon\":2.3488,\"lat\":48.8534},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"nuageux\",\"icon\":\"04d\"}],\"base\":\"stations\",\"main\":{\"temp\":288.05,\"feels_like\":287.35,\"temp_min\":286.08,\"temp_max\":289.92,\"pressure\":1018,\"humidity\":67},\"visibility\":10000,\"wind\":{\"speed\":4.02,\"deg\":224,\"gust\":9.83},\"clouds\":{\"all\":75},\"dt\":1621950361,\"sys\":{\"type\":2,\"id\":2012208,\"country\":\"FR\",\"sunrise\":1621915050,\"sunset\":1621971476},\"timezone\":7200,\"id\":2988507,\"name\":\"Paris\",\"cod\":200}");
        //When
        Weather weather = new WeatherService(webUtils).getWeatherOfTheCity("Paris");
        //Then
        Assertions.assertThat(weather).isNotNull();
        Assertions.assertThat(weather.getSunsetDate()).isEqualTo("21:37");
        Assertions.assertThat(weather.getsunriseDate()).isEqualTo("5:57");
        Assertions.assertThat(weather.getDescription()).isEqualTo("nuageux");
        Assertions.assertThat(weather.getHumidity()).isEqualTo(67);
        Assertions.assertThat(weather.getTemp()).isEqualTo(288.05);
        Assertions.assertThat(weather.getId()).isEqualTo(803);
    }
}