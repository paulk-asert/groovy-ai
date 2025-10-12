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
package ollama4j

import io.github.ollama4j.Ollama
import io.github.ollama4j.models.chat.OllamaChatMessageRole
import io.github.ollama4j.models.chat.OllamaChatRequestBuilder

def ollama = new Ollama(requestTimeoutSeconds: 300)
println "Found ollama: ${ollama.ping()}"

def prompt = '"What are 4 interesting things to do while I am on vacation in Caloundra?"'
def builder = OllamaChatRequestBuilder.builder()
    .withModel('mistral:7b')

def request = builder
    .withMessage(OllamaChatMessageRole.USER, prompt)
    .build()

def result = ollama.chat(request, null)
println "First answer: " + result.responseModel.message.response

def prompt2 = "If I had half a day and can only go to one, which would you recommend?"
request = builder
    .withMessages(result.chatHistory)
    .withMessage(OllamaChatMessageRole.USER, prompt2)
    .build()

result = ollama.chat(request, null)
println "Second answer: " + result.responseModel.message.response

/*
Found ollama: true
Asking model: {
  "model" : "mistral:7b",
  "stream" : false,
  "messages" : [ {
    "role" : "user",
    "thinking" : null,
    "images" : null,
    "content" : "\"What are 4 interesting things to do while I am on vacation in Caloundra?\"",
    "tool_calls" : [ ]
  } ],
  "tools" : [ ],
  "think" : false,
  "useTools" : true
}
First answer:  1. Visit the beautiful beaches: Caloundra is known for its stunning coastline, with some of the most popular beaches including Kings Beach, Moffat Beach, and Bulcock Beach. These beaches offer swimming, surfing, and sunbathing opportunities, as well as picturesque views of the ocean.

2. Visit the Underwater World SEA LIFE Sunshine Coast: This aquarium is home to a variety of marine life, including sharks, rays, turtles, and fish. Visitors can walk through the Shark Shipwreck Tunnel, see penguins up close, and even touch starfish in the Rockpool Experience.

3. Explore the Glass House Mountains: Just a short drive from Caloundra, the Glass House Mountains offer breathtaking views and a unique geological formation. You can hike up to the summit of one of the mountains for panoramic vistas of the surrounding area.

4. Visit the Eumundi Markets: Located approximately 30 minutes drive from Caloundra, the Eumundi Markets are one of the largest and longest-running art and craft markets in Australia. The market features over 600 stalls selling a wide variety of goods, including locally made arts and crafts, food, clothing, and souvenirs.
Asking model: {
  "model" : "mistral:7b",
  "stream" : false,
  "messages" : [ {
    "role" : "user",
    "thinking" : null,
    "images" : null,
    "content" : "\"What are 4 interesting things to do while I am on vacation in Caloundra?\"",
    "tool_calls" : [ ]
  }, {
    "role" : "assistant",
    "thinking" : null,
    "images" : null,
    "content" : " 1. Visit the beautiful beaches: Caloundra is known for its stunning coastline, with some of the most popular beaches including Kings Beach, Moffat Beach, and Bulcock Beach. These beaches offer swimming, surfing, and sunbathing opportunities, as well as picturesque views of the ocean.\n\n2. Visit the Underwater World SEA LIFE Sunshine Coast: This aquarium is home to a variety of marine life, including sharks, rays, turtles, and fish. Visitors can walk through the Shark Shipwreck Tunnel, see penguins up close, and even touch starfish in the Rockpool Experience.\n\n3. Explore the Glass House Mountains: Just a short drive from Caloundra, the Glass House Mountains offer breathtaking views and a unique geological formation. You can hike up to the summit of one of the mountains for panoramic vistas of the surrounding area.\n\n4. Visit the Eumundi Markets: Located approximately 30 minutes drive from Caloundra, the Eumundi Markets are one of the largest and longest-running art and craft markets in Australia. The market features over 600 stalls selling a wide variety of goods, including locally made arts and crafts, food, clothing, and souvenirs.",
    "tool_calls" : null
  }, {
    "role" : "user",
    "thinking" : null,
    "images" : null,
    "content" : "If I had half a day and can only go to one, which would you recommend?",
    "tool_calls" : [ ]
  } ],
  "tools" : [ ],
  "think" : false,
  "useTools" : true
}
Second answer:  If you have half a day and can only choose one activity in Caloundra, I would recommend visiting the beautiful Kings Beach. This beach offers a variety of activities for all ages, including swimming, surfing, and sunbathing. The beach also has a children's playground, picnic areas, and BBQ facilities, making it a great spot for families. Additionally, Kings Beach has several cafes and restaurants nearby where you can grab a bite to eat or relax with a drink after spending some time at the beach.

Another option if you prefer indoor activities would be visiting the Underwater World SEA LIFE Sunshine Coast. This aquarium is home to a variety of marine life, including sharks, rays, turtles, and fish. The Shark Shipwreck Tunnel, penguin exhibit, and Rockpool Experience are particularly popular attractions at the aquarium.

Ultimately, the choice between Kings Beach and Underwater World SEA LIFE Sunshine Coast will depend on your personal preferences and what you enjoy doing on vacation. Both options offer unique experiences that showcase the beauty of Caloundra's coastline and marine life.
*/
