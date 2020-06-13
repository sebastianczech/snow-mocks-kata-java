package snow;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import snow.dependencies.MunicipalServices;
import snow.dependencies.WeatherForecastService;

class SnowRescueServiceTest {

    @Test
    void send_sander_when_temperature_belowe_zero() {
        // given
        WeatherForecastService weatherForecastService = Mockito.mock(WeatherForecastService.class);
        MunicipalServices municipalServices = Mockito.mock(MunicipalServices.class);
        Mockito.when(weatherForecastService.getAverageTemperatureInCelsius()).thenReturn(-1);
        SnowRescueService snowRescueService = new SnowRescueService(weatherForecastService, municipalServices, null);

        // when
        snowRescueService.checkForecastAndRescue();

        // then
        Mockito.verify(municipalServices).sendSander();
    }

}
