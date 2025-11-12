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

var prompt2 = 'If I had half a day and can only go to one, which would you recommend?'
var response2 = assistant.chat(prompt2)

println """
Four things:
$response

Best thing:
$response2
"""

/*
Four things:
 1. Visit the Coastal Walk: The Caloundra Coastal Walk offers stunning views of the coastline, beaches, and the Pumicestone Passage. It's a great way to enjoy the outdoors and take in the beautiful scenery.

2. Explore the Bulcock Beach: This is one of the most popular beaches in Caloundra. You can swim, sunbathe, or just relax on the beach. Nearby, you'll find shops, cafes, and restaurants for a post-beach refreshment.

3. Visit the Australian Zoo: Made famous by Steve Irwin, the zoo is home to a wide variety of Australian wildlife. It's a great place for both children and adults to learn about local animals and conservation efforts.

4. Take a day trip to the Glass House Mountains: Just a short drive from Caloundra, these stunning volcanic peaks offer hiking trails, scenic lookouts, and a unique geographical feature that's distinctive to the region.

Best thing:
 If you only have half a day and can only go to one place, I would recommend visiting the Bulcock Beach. It offers a relaxing beach experience with beautiful views, as well as easy access to shops, cafes, and restaurants if you'd like to grab a bite to eat or do some souvenir shopping.

The Australian Zoo is also a great option, but it may take longer to get there, and you might not have enough time to fully enjoy everything the zoo has to offer in just half a day. The Glass House Mountains are another fantastic choice, but they require a bit more travel time as well. Bulcock Beach provides a perfect balance of relaxation and amenities within easy reach.

*/
