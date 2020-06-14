package snow;

import snow.dependencies.MunicipalServices;
import snow.dependencies.PressService;
import snow.dependencies.SnowplowMalfunctioningException;
import snow.dependencies.WeatherForecastService;

class SnowRescueService {

	private static final int TEMPERATURE_WHEN_SEND_SANDER = 0;
	private static final int TEMPERATURE_WHEN_PRESS_ALERT = -10;
	private static final int SNOWFALL_WHEN_SEND_1_SNOWPLOW = 3;
	private static final int SNOWFALL_WHEN_SEND_2_SNOWPLOWS = 5;
	private static final int SNOWFALL_WHEN_SEND_3_SNOWPLOWS = 10;

	private final WeatherForecastService weatherForecastService;
	private final MunicipalServices municipalServices;
	private final PressService pressService;

	SnowRescueService(WeatherForecastService weatherForecastService, MunicipalServices municipalServices, PressService pressService) {
		this.weatherForecastService = weatherForecastService;
		this.municipalServices = municipalServices;
		this.pressService = pressService;
	}

	void checkForecastAndRescue() {
		if (weatherForecastService.getAverageTemperatureInCelsius() < TEMPERATURE_WHEN_SEND_SANDER) {
			sendSander();
		}
		for (int i=0; i<numberOfSnowplowToSend(weatherForecastService.getSnowFallHeightInMM()); i++) {
			sendSnowplow();
		}
		if (weatherForecastService.getSnowFallHeightInMM() > SNOWFALL_WHEN_SEND_3_SNOWPLOWS
				&& weatherForecastService.getAverageTemperatureInCelsius() < TEMPERATURE_WHEN_PRESS_ALERT) {
			sendPressAlert();
		}
	}

	private int numberOfSnowplowToSend(int snowFallHeightInMM) {
		if (snowFallHeightInMM > SNOWFALL_WHEN_SEND_3_SNOWPLOWS) {
			return 3;
		} else if (snowFallHeightInMM > SNOWFALL_WHEN_SEND_2_SNOWPLOWS) {
			return 2;
		} else {
			return 1;
		}
	}

	private void sendPressAlert() {
		pressService.sendWeatherAlert();
	}

	private void sendSander() {
		municipalServices.sendSander();
	}

	private void sendSnowplow() {
		try {
			municipalServices.sendSnowplow();
		} catch (SnowplowMalfunctioningException snowplowException) {
			municipalServices.sendSnowplow();
		}
	}

}
