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

import dev.langchain4j.memory.chat.MessageWindowChatMemory
import dev.langchain4j.model.ollama.OllamaChatModel
import dev.langchain4j.service.AiServices
import domain.Activity

import static dev.langchain4j.model.chat.Capability.RESPONSE_FORMAT_JSON_SCHEMA

interface HolidayBot {
    List<Activity> extractActivitiesFrom(String text)
}

var model = OllamaChatModel.builder()
    .baseUrl("http://localhost:11434")
    .supportedCapabilities(RESPONSE_FORMAT_JSON_SCHEMA)
    .timeout(Duration.ofMinutes(5))
    .modelName("mistral:7b")
    .build()

var chatMemory = MessageWindowChatMemory.withMaxMessages(10)

var bot = AiServices.builder(HolidayBot)
    .chatModel(model)
    .chatMemory(chatMemory)
    .build()

var prompt = '''
What are 4 interesting things to do for a weekend away in Caloundra?
Return holiday activity suggestions as structured JSON matching Itinerary.
Give a description, location, day of weekend, and suggested time of day for each of the four activities.
'''
var response = bot.extractActivitiesFrom(prompt)

var prompt2 = '''
If my only spare time is Sunday morning, and I can only go to one activity, which would you recommend?
'''
var response2 = bot.extractActivitiesFrom(prompt2)

println """
Four things:
${response.join('\n')}

Best thing:
${response2.join('\n')}
"""

/*
Four things:
Activity(Visit Kings Beach, Kings Beach, Caloundra, Saturday, Morning/Afternoon)
Activity(Explore the Coastal Walk from Bulcock Beach to Shelly Beach, Bulcock Beach to Shelly Beach, Caloundra, Saturday, Early Afternoon/Late Afternoon)
Activity(Relax at the Day Spa on Golden Beach, Golden Beach, Caloundra, Sunday, Morning/Afternoon)
Activity(Enjoy Sunset Dinner Cruise, Pelican Waters or Mooloolaba, Caloundra, Sunday, Late Afternoon/Evening)

Best thing:
Activity(Relax at the Day Spa on Golden Beach, Golden Beach, Caloundra, Sunday, Morning)
*/
