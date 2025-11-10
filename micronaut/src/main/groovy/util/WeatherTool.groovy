/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
