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
package springai

import domain.Weather
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.ollama.api.OllamaChatOptions
import org.springframework.ai.tool.annotation.Tool
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.stereotype.Component

import java.time.temporal.TemporalAdjusters

@Component
class WeekendTool {
    @Tool(description = 'The LocalDate of the start of the coming weekend')
    LocalDate getWeekend() {
        LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY))
    }
}

@Component
class WeatherTool {
    static Integer fakeDay = 0

    @Tool(description = 'The expected weather including forecast, min and max temperature in Celsius for a given location and LocalDate')
    Weather getWeather(String location, LocalDate date) {
        var fakeWeather = [0: [Caloundra: new Weather('Sunny and Hot', 30, 37)],
                           1: [Caloundra: new Weather('Raining', 5, 15)]]
        fakeWeather[fakeDay++ % fakeWeather.size()][location]
    }
}

@SpringBootApplication
void main() {
    var prompt = '''
Recommend an interesting thing to see in Caloundra for each day of this coming weekend.
Factor in expected weather when making recommendations. Do not hallucinate weather or dates.
'''
    try(var context = SpringApplication.run(Holiday)) {
        var chatClient = context.getBean(ChatClient.Builder).build()
        var weekend = context.getBean(WeekendTool)
        var weather = context.getBean(WeatherTool)
        var options = OllamaChatOptions.builder().model('qwen3:8b').build()
        println chatClient
                .prompt(prompt)
                .options(options)
                .tools(weekend, weather)
                .call()
                .content()
    }
}
/*
Here’s a weather-informed recommendation for Caloundra over the coming weekend (November 15-16, 2025):

**Saturday, November 15 (Sunny & Hot: 30°C–37°C)**
Perfect for outdoor adventures!
- **Caloundra Spit** – Explore the scenic coastal walk with panoramic ocean views.
- **Snorkeling at Mooloolaba Beach** – Clear waters and vibrant marine life.
- **Glass House Mountains Day Trip** – A short drive offers dramatic landscapes and hiking.

**Sunday, November 16 (Raining: 5°C–15°C)**
Opt for indoor attractions or sheltered activities:
- **Caloundra Cultural Centre** – Discover local art and history.
- **Caloundra Regional Gallery** – Enjoy contemporary exhibitions.
- **Indoor Water Playground** – A splash-filled escape from the rain.

Always check for real-time weather updates closer to the date! 🌞🌧️
*/
