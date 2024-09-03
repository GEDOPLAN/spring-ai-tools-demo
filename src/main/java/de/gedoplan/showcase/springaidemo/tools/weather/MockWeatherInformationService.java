package de.gedoplan.showcase.springaidemo.tools.weather;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.function.Function;

public class MockWeatherInformationService implements Function<WeatherRequest, WeatherResponse> {

    private Logger logger = LoggerFactory.getLogger(MockWeatherInformationService.class);

   private final Map<String, WeatherResponse> temperatureMap = Map.ofEntries(
            Map.entry("Bielefeld", new WeatherResponse.Success("Bielefeld", 20.1, TempUnit.CELSIUS)),
            Map.entry("Berlin", new WeatherResponse.Success("Berlin", 25.6, TempUnit.CELSIUS)),
            Map.entry("Köln", new WeatherResponse.Success("Köln", 27.2, TempUnit.CELSIUS))
    );

    @Override
    public WeatherResponse apply(WeatherRequest request) {
        logger.info("weatherRequest: {}", request);
        if (request == null) return new WeatherResponse.Failure("Error: Could not parse request.");

        WeatherResponse weatherResponse = temperatureMap.getOrDefault(request.location(), null);
        if (weatherResponse == null) {
            WeatherResponse returnWeatherResponse = new WeatherResponse.Failure("Error: No weather information for location "+ request.location() +" available.");
            logger.info("weatherResponse: {}", returnWeatherResponse);
            return returnWeatherResponse;
        } else {
            WeatherResponse returnWeatherResponse = ((WeatherResponse.Success) weatherResponse).withTempUnit(request.unit());
            logger.info("weatherResponse: {}", returnWeatherResponse);
            return returnWeatherResponse;
        }
    }
}
