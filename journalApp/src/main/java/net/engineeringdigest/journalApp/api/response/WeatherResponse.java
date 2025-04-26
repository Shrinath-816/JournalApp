package net.engineeringdigest.journalApp.api.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.omg.CORBA.Current;

import java.util.List;
@Getter
@Setter
public class WeatherResponse{


    private Current current;


    @Getter
    @Setter
    public class Current {
        @JsonProperty("observation_time")
        private String observationTime;
        private int temperature;
        @JsonProperty("weather_code")
        private int weatherCode;

        private int feelslike;

    }

}
