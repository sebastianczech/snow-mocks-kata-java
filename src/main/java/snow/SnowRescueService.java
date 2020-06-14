package snow;

import snow.dependencies.MunicipalServices;
import snow.dependencies.PressService;
import snow.dependencies.SnowplowMalfunctioningException;
import snow.dependencies.WeatherForecastService;

public class SnowRescueService {

	private static final int TEMPERATURE_WHEN_SEND_SANDER = 0;
	private static final int SNOWFALL_WHEN_SEND_SNOWPLOW = 3;

	private final WeatherForecastService weatherForecastService;
	private final MunicipalServices municipalServices;
	private final PressService pressService;

	public SnowRescueService(WeatherForecastService weatherForecastService, MunicipalServices municipalServices, PressService pressService) {
		this.weatherForecastService = weatherForecastService;
		this.municipalServices = municipalServices;
		this.pressService = pressService;
	}

	void checkForecastAndRescue() {
		if (weatherForecastService.getAverageTemperatureInCelsius() < TEMPERATURE_WHEN_SEND_SANDER) {
			municipalServices.sendSander();
		}
		if (weatherForecastService.getSnowFallHeightInMM() > SNOWFALL_WHEN_SEND_SNOWPLOW) {
			try {
				municipalServices.sendSnowplow();
			}
			catch (SnowplowMalfunctioningException snowplowException) {
				municipalServices.sendSnowplow();
			}

		}
	}

}
