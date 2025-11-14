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

import dev.langchain4j.model.ollama.OllamaChatModel
import dev.langchain4j.memory.chat.MessageWindowChatMemory
import dev.langchain4j.service.AiServices

interface HolidayAssistant {
    String chat(String message)
}

var model = OllamaChatModel.builder()
    .baseUrl("http://localhost:11434")
    .timeout(Duration.ofMinutes(5))
    .modelName("mistral:7b")
    .build()

var chatMemory = MessageWindowChatMemory.withMaxMessages(10)

var assistant = AiServices.builder(HolidayAssistant)
    .chatModel(model)
    .chatMemory(chatMemory)
    .build()

var prompt = 'What are 4 interesting things to do while I am on vacation in Caloundra?'
var response = assistant.chat(prompt)

var prompt2 = '''
It might rain at some point on the weekend, so can you give me
a very short description of a single backup alternative if it rains?
Make it different to your previous suggestions since I am not
sure which ones I will have already seen by the time it rains.
'''
var response2 = assistant.chat(prompt2)

println """
Four things:
$response

If it rains:
$response2
"""

/*
Four things:
 1. Visit the beautiful beaches: Caloundra is known for its stunning beaches, with Mooloolaba Beach and Kings Beach being particularly popular. You can spend your days swimming, sunbathing, or even surfing.

2. Explore the Underwater World SEA LIFE Sunshine Coast: This aquarium offers an amazing opportunity to get up close and personal with a variety of marine life, including sharks, stingrays, turtles, and seals.

3. Visit the Bulcock Beach Esplanade: This is a great spot for shopping, dining, and people-watching. The esplanade offers a range of boutiques, cafes, and restaurants. Don't forget to check out the local markets that are held regularly.

4. Take a day trip to Australia Zoo: Made famous by the Crocodile Hunter, Steve Irwin, this zoo is a must-visit for animal lovers. It's home to a wide variety of Australian wildlife and offers interactive experiences and shows throughout the day.

If it rains:
 If it rains, an indoor activity that you might enjoy is visiting the Queensland Air Museum in Caboolture, which is a short drive from Caloundra. The museum houses one of Australia's largest collections of aircraft and aviation artifacts, including military planes, helicopters, and memorabilia. It offers a fascinating look at the history of Australian aviation and is suitable for all ages.
 */
