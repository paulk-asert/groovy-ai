package util

import dev.langchain4j.agent.tool.Tool
import io.micronaut.context.annotation.Executable
import jakarta.inject.Singleton

import java.time.LocalDate

@Singleton
class WeatherTool {
    @Tool('Gets the expected weather forecast including temperature for a given city and date')
//    @Executable
    String getWeather(String city, LocalDate date) {
        println "Looking up weather for $city on $date"
        var fakeWeather = [Auckland: ['sunny', 26], 'Mt Hutt': ['hot', 38]]
        var (forecast, temp) = fakeWeather[city]
        "In ${city} on ${date}, the weather is expected to be ${forecast} with around ${temp}°C."
    }
}
