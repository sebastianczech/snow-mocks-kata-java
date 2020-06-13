package snow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import snow.dependencies.MunicipalServices;
import snow.dependencies.WeatherForecastService;

@ExtendWith(MockitoExtension.class)
class SnowRescueServiceTest {

    @Mock WeatherForecastService weatherForecastService;
    @Mock MunicipalServices municipalServices;

    private SnowRescueService snowRescueService;

    @BeforeEach
    void setup() {
        Mockito.when(weatherForecastService.getAverageTemperatureInCelsius()).thenReturn(-1);
        snowRescueService = new SnowRescueService(weatherForecastService, municipalServices, null);
    }

    @Test
    void send_sander_when_temperature_below_zero() {
        // given
//        WeatherForecastService weatherForecastService = Mockito.mock(WeatherForecastService.class);
//        MunicipalServices municipalServices = Mockito.mock(MunicipalServices.class);
//        Mockito.when(weatherForecastService.getAverageTemperatureInCelsius()).thenReturn(-1);
//        SnowRescueService snowRescueService = new SnowRescueService(weatherForecastService, municipalServices, null);

        // when
        snowRescueService.checkForecastAndRescue();

        // then
        Mockito.verify(municipalServices).sendSander();
    }

}
