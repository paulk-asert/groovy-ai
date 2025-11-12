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

var prompt = 'What are 4 interesting things to do for a long weekend vacation in Caloundra?'
var response = bot.extractActivitiesFrom(prompt)

var prompt2 = 'If I had half a day and can only go to one, which would you recommend?'
var response2 = bot.extractActivitiesFrom(prompt2)

println """
Four things:
${response.join('\n')}

Best thing:
${response2.join('\n')}
"""

/*
Four things:
Activity(Visit Kings Beach, Caloundra, Queensland, Australia, null, Full day or half-day, depending on your preference and activities chosen at the beach such as swimming, surfing, or sunbathing.)
Activity(Explore the Bulcock Beach Markets, Bulcock Street, Caloundra, Queensland, Australia, null, Saturday mornings from 8 am to 1 pm for shopping local produce, arts and crafts, food vendors, and live music.)
Activity(Hike the Glass House Mountains, Glass House Mountains National Park, Beerburrum, Queensland, Australia, null, A full day trip to explore the 12 volcanic plugs and hike various trails with spectacular views.)
Activity(Relax at the Spa on Kings, 59 Bulcock St, Caloundra, Queensland, Australia, null, Spend a relaxing afternoon at this day spa offering massages, facials, and various beauty treatments.)

Best thing:
Activity(Visit Kings Beach, Caloundra, Queensland, Australia, null, If you only have half a day and want to enjoy the beach, Kings Beach is the best option for swimming, surfing, or sunbathing.)
Activity(Bulcock Beach Markets, Bulcock Street, Caloundra, Queensland, Australia, null, If you're interested in shopping local produce, arts and crafts, food vendors, and live music, the Bulcock Beach Markets would be a great choice. However, it is only open on Saturday mornings.)
Activity(Relax at the Spa on Kings, 59 Bulcock St, Caloundra, Queensland, Australia, null, If you're looking to relax and indulge in a spa day, The Spa on Kings offers massages, facials, and various beauty treatments. A half-day trip would be sufficient for a rejuvenating experience.)
*/
