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
        Mockito.when(webUtils.getPageContents(Mockito.anyString())).thenReturn("{\"coord\":{\"lon\":2.3488,\"lat\":48.8534},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"légère pluie\",\"icon\":\"10d\"}],\"base\":\"stations\",\"main\":{\"temp\":286.82,\"feels_like\":285.97,\"temp_min\":284.41,\"temp_max\":288.58,\"pressure\":1019,\"humidity\":66},\"visibility\":10000,\"wind\":{\"speed\":0.89,\"deg\":217,\"gust\":10.28},\"rain\":{\"1h\":0.15},\"clouds\":{\"all\":75},\"dt\":1621938919,\"sys\":{\"type\":2,\"id\":2012208,\"country\":\"FR\",\"sunrise\":1621915050,\"sunset\":1621971476},\"timezone\":7200,\"id\":2988507,\"name\":\"Paris\",\"cod\":200}");
        //When
        Weather quote = new CitationService(webUtils).getQuoteOfTheDay();
        //Then
        Assertions.assertThat(quote).isNotNull();
        Assertions.assertThat(quote.getQuote()).isEqualTo("Your success will not be determined by your gender or your ethnicity, but only on the scope of your dreams and your hard work to achieve them.");
        Assertions.assertThat(quote.getAuthor()).isEqualTo("Zaha Hadidd");
    }
}