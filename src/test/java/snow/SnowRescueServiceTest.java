package snow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import snow.dependencies.MunicipalServices;
import snow.dependencies.PressService;
import snow.dependencies.WeatherForecastService;

@ExtendWith(MockitoExtension.class)
class SnowRescueServiceTest {

    @Mock
    WeatherForecastService weatherForecastService;

    @Mock
    MunicipalServices municipalServices;

    @Mock
    PressService pressService;

    @Test
    void send_sander_when_temperature_below_zero() {
        // given
//        WeatherForecastService weatherForecastService = Mockito.mock(WeatherForecastService.class);
//        MunicipalServices municipalServices = Mockito.mock(MunicipalServices.class);
        Mockito.when(weatherForecastService.getAverageTemperatureInCelsius()).thenReturn(-1);
        SnowRescueService snowRescueService = new SnowRescueService(weatherForecastService, municipalServices, null);

        // when
        snowRescueService.checkForecastAndRescue();

        // then
        Mockito.verify(municipalServices).sendSander();
    }

    @Test
    void send_snowplow_when_snowfall_more_than_3mm() {
        // given
        Mockito.when(weatherForecastService.getSnowFallHeightInMM()).thenReturn(4);
        SnowRescueService snowRescueService = new SnowRescueService(weatherForecastService, municipalServices, null);

        // when
        snowRescueService.checkForecastAndRescue();

        // then
        Mockito.verify(municipalServices).sendSnowplow();
    }

}
