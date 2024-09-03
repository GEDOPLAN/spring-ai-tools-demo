package de.gedoplan.showcase.springaidemo.tools.traffic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.function.Function;


public class MockTrafficInformationService
        implements Function<TrafficRequest, List<TrafficResponse>> {

    Logger logger = LoggerFactory.getLogger(MockTrafficInformationService.class);

    private final Map<Motorway, List<TrafficResponse>> trafficJamMap = Map.ofEntries(
            Map.entry(Motorway.A1, List.of(
                new TrafficResponse("Dortmund", "Euskirchen", 2.0, LengthUnit.KILOMETRES),
                new TrafficResponse("Köln", "Dortmund", 4.0, LengthUnit.KILOMETRES)
            )),
            Map.entry(Motorway.A2, List.of(
                    new TrafficResponse("Oberhausen", "Dortmund", 8.5, LengthUnit.KILOMETRES),
                    new TrafficResponse("Bielefeld", "Hannover", 3.0, LengthUnit.KILOMETRES)
            ))
    );

    @Override
    public List<TrafficResponse> apply(TrafficRequest request) {
        logger.info("trafficRequest: {}", request);
        if (request.motorway() == null) return List.of();
        var trafficJams = trafficJamMap.getOrDefault(request.motorway(), List.of())
                .stream().map(response -> response.withLengthUnit(response.unit())).toList();
        logger.info("trafficResponse: {}", trafficJams);
        return trafficJams;
    }

}
