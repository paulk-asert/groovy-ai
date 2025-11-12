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

import dev.langchain4j.data.message.UserMessage
import dev.langchain4j.model.ollama.OllamaChatModel

var model = OllamaChatModel.builder()
    .baseUrl("http://localhost:11434")
    .timeout(Duration.ofMinutes(5))
    .modelName("mistral:7b")
    .build()

var prompt = 'What are 4 interesting things to do while I am on vacation in Caloundra?'
var response = model.chat(new UserMessage(prompt))

var prompt2 = 'If I had half a day and can only go to one, which would you recommend?'
var response2 = model.chat(response.aiMessage(), new UserMessage(prompt2))

println """
Four things:
${response.aiMessage().text()}

Best thing:
${response2.aiMessage().text()}
"""

/*
Four things:
 1. Visit the Bulcock Beach: This is a beautiful patrolled beach that offers swimming, picnicking, and walking along the esplanade. It's also home to a weekly Sunday markets where you can find local produce, art, and crafts.

2. Explore the Coastal Walk: The Caloundra Coastal Path offers stunning views of the coastline, Pumicestone Passage, and Bribie Island. It's a great way to enjoy the outdoors and get some exercise.

3. Visit the UnderWater World SEA LIFE Mooloolaba: This is a fantastic aquarium located in Mooloolaba, just a short drive from Caloundra. You can see a variety of marine life, including sharks, turtles, and seahorses.

4. Take a Day Trip to the Glass House Mountains: Just a 30-minute drive away, these spectacular granite peaks provide great hiking opportunities and offer stunning views of the surrounding landscape. Don't miss the lookout at Tibrogargan for the best view!

Best thing:
 If you only have half a day and can only choose one attraction, I would recommend visiting the UnderWater World SEA LIFE Mooloolaba. It's an excellent aquarium that offers a fascinating glimpse into the marine life of the region, and it's suitable for people of all ages.

The UnderWater World is home to a variety of marine animals, including sharks, turtles, stingrays, seahorses, and many more. You can also participate in interactive experiences such as feeding the sharks or holding a starfish. The aquarium also offers educational programs and behind-the-scenes tours for those interested in learning more about marine conservation.

While the Coastal Walk and Glass House Mountains are worth visiting if you have more time, they require more planning and travel time, so I would recommend UnderWater World SEA LIFE Mooloolaba as the best option for a half-day visit.
*/
