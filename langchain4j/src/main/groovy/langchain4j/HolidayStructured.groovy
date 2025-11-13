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
What are 4 interesting things to do for a long weekend vacation in Caloundra?
Provide location, and suggested non-overlapping day and time for each activity.
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
Activity(Visit Australia Zoo, Beerwah, Queensland (Approx. 30 minutes drive from Caloundra), Day 1 - Friday, 9:00 AM - 5:00 PM)
Activity(Explore Kings Beach and the Coastal Walk, Caloundra, Queensland, Day 2 - Saturday, 8:00 AM - Afternoon)
Activity(Relax at Bulcock Beach Market, Bulcock Street, Caloundra, Day 3 - Sunday, 6:00 AM - 1:00 PM)
Activity(Explore the Glass House Mountains, Glass House Mountains, Queensland (Approx. 45 minutes drive from Caloundra), Day 4 - Monday, 9:00 AM - 3:00 PM)

Best thing:
Activity(Relax at Bulcock Beach Market, Bulcock Street, Caloundra, Sunday, 6:00 AM - 1:00 PM)
*/
