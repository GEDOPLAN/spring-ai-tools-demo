package de.gedoplan.showcase.springaidemo.config;

import de.gedoplan.showcase.springaidemo.tools.traffic.MockTrafficInformationService;
import de.gedoplan.showcase.springaidemo.tools.traffic.TrafficRequest;
import de.gedoplan.showcase.springaidemo.tools.traffic.TrafficResponse;
import de.gedoplan.showcase.springaidemo.tools.weather.MockWeatherInformationService;
import de.gedoplan.showcase.springaidemo.tools.weather.WeatherRequest;
import de.gedoplan.showcase.springaidemo.tools.weather.WeatherResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.List;
import java.util.function.Function;

@Configuration
public class AiClientConfig {

    @Bean
    ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder.build();
    }

    @Bean
    @Description("Get traffic jam information for motorway.")
    public Function<TrafficRequest, List<TrafficResponse>> trafficJamFunction() {
        return new MockTrafficInformationService();
    }

    @Bean
    @Description("Get weather information for location.")
    public Function<WeatherRequest, WeatherResponse> weatherFunction() {
        return new MockWeatherInformationService();
    }

}
