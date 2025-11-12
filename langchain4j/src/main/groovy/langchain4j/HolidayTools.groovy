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
package langchain4j

import dev.langchain4j.agent.tool.Tool
import dev.langchain4j.memory.chat.MessageWindowChatMemory
import dev.langchain4j.model.ollama.OllamaChatModel
import dev.langchain4j.service.AiServices
import domain.Weather
import groovy.transform.Field

import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

interface HolidayAssistantTools {
    String chat(String message)
}

@Tool("The LocalDate of the start of the coming weekend")
LocalDate getWeekend() {
    LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY))
}

@Field static Integer fakeDay = 0

@Tool('The expected domain.Weather including weather forecast, min and max temperature in Celsius for a given location and LocalDate')
Weather getWeather(String location, LocalDate date) {
    var fakeWeather = [0: [Caloundra: new Weather('Sunny and Hot', 30, 37)],
                       1: [Caloundra: new Weather('Raining', 5, 15)]]
    fakeWeather[fakeDay++][location]
}

var model = OllamaChatModel.builder()
    .baseUrl("http://localhost:11434")
    .timeout(Duration.ofMinutes(5))
    .modelName("qwen3:8b")
//    .logRequests(true)
//    .logResponses(true)
    .build()

var chatMemory = MessageWindowChatMemory.withMaxMessages(10)

var assistant = AiServices.builder(HolidayAssistantTools)
    .chatModel(model)
    .chatMemory(chatMemory)
    .tools(this)
    .build()

var prompt = '''
Recommend an interesting thing to see in Caloundra for each day of this coming weekend.
Factor in expected weather when making recommendations. Do not hallucinate weather or dates.
'''
var response = assistant.chat(prompt)

println """
Preparing recommendations as at: ${LocalDate.now()}
Interesting things:
$response
"""

/*
Preparing recommendations as at: 2025-11-12
Interesting things:
Here’s a weather-aware itinerary for Caloundra’s upcoming weekend (November 15–16, 2025):

---

**Saturday, November 15 (Sunny & Hot: 30–37°C)**
🌞 **Recommended Activity:**
**Great Sandy Strait Snorkeling Tour** – Explore vibrant marine life in calm, shallow waters. Pair with a beachside picnic at **Caloundra Beach** (bring sunscreen and plenty of water!).

**Why it works:** Clear skies and warm temps perfect for water activities. Avoid midday sun; plan trips early.

---

**Sunday, November 16 (Raining: 5–15°C)**
🌧️ **Recommended Activity:**
**Caloundra Cultural Centre Visit** – Explore local art, history, and Indigenous culture indoors. Follow with a warm cup of coffee at **The Beachside Coffee Co.** (grab a sweater for the cooler temps!).

**Why it works:** Rainy weather suits indoor attractions. Dress in layers and prioritize dry, sheltered spots.

---

Always check local event calendars for real-time updates!
*/
