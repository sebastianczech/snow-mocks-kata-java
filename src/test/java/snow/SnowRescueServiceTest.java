package snow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import snow.dependencies.MunicipalServices;
import snow.dependencies.PressService;
import snow.dependencies.SnowplowMalfunctioningException;
import snow.dependencies.WeatherForecastService;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class SnowRescueServiceTest {

    @Mock
    WeatherForecastService weatherForecastService;

    @Mock
    MunicipalServices municipalServices;

    @Mock
    PressService pressService;
    private SnowRescueService snowRescueService;

    @BeforeEach
    void setUp() {
        snowRescueService = new SnowRescueService(weatherForecastService, municipalServices, pressService);
    }

    @Test
    void send_sander_when_temperature_below_zero() {
        // given
//        WeatherForecastService weatherForecastService = Mockito.mock(WeatherForecastService.class);
//        MunicipalServices municipalServices = Mockito.mock(MunicipalServices.class);
        Mockito.when(weatherForecastService.getAverageTemperatureInCelsius()).thenReturn(-1);

        // when
        snowRescueService.checkForecastAndRescue();

        // then
        Mockito.verify(municipalServices).sendSander();
    }

    @Test
    void send_snowplow_when_snowfall_more_than_3mm() {
        // given
        Mockito.when(weatherForecastService.getSnowFallHeightInMM()).thenReturn(4);

        // when
        snowRescueService.checkForecastAndRescue();

        // then
        Mockito.verify(municipalServices).sendSnowplow();
    }

    @Test
    void send_snowplow_when_previous_snowplow_is_broken() {
        // given
        Mockito.when(weatherForecastService.getSnowFallHeightInMM()).thenReturn(4);
        Mockito.doThrow(SnowplowMalfunctioningException.class).when(municipalServices).sendSnowplow();

        // when, //then
        assertThatThrownBy(snowRescueService::checkForecastAndRescue).isInstanceOf(SnowplowMalfunctioningException.class);

        // then
        Mockito.verify(municipalServices, times(2)).sendSnowplow();
    }

    @Test
    void send_2_snowplows_when_snowfall_more_than_5mm() {
        // given
        Mockito.when(weatherForecastService.getSnowFallHeightInMM()).thenReturn(6);

        // when
        snowRescueService.checkForecastAndRescue();

        // then
        Mockito.verify(municipalServices, times(2)).sendSnowplow();
    }

    @Test
    void send_3_snowplows_1_sander_call_to_press_when_snowfall_more_than_10mm_and_temperature_below_10() {
        // given
        Mockito.when(weatherForecastService.getSnowFallHeightInMM()).thenReturn(11);
        Mockito.when(weatherForecastService.getAverageTemperatureInCelsius()).thenReturn(-11);

        // when
        snowRescueService.checkForecastAndRescue();

        // then
        Mockito.verify(municipalServices, times(3)).sendSnowplow();
        Mockito.verify(municipalServices).sendSander();
        Mockito.verify(pressService).sendWeatherAlert();
    }

}
