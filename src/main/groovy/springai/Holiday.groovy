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

import org.springframework.ai.chat.client.ChatClient
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
void main() {
    try(var context = SpringApplication.run(Holiday)) {
        var chatClient = context.getBean(ChatClient.Builder).build()
        var response = chatClient
                .prompt("What are some interesting things to do while I am on vacation in Caloundra?")
                .call()
        println "Response:\n" + response.content()
    }
}
/*
Response:
 Caloundra, located on the Sunshine Coast of Australia, offers a variety of activities that cater to different interests. Here are some suggestions for an enjoyable vacation:

1. Beaches: Caloundry has several beautiful beaches, including Kings Beach, Moffat Beach, and Bulcock Beach. You can swim, sunbathe, surf, or just enjoy the stunning views.

2. Visit the Underwater World SEA LIFE Mooloolaba: This aquarium is home to a diverse range of marine life, including sharks, turtles, and seahorses. It's a great place for both children and adults to learn about and interact with marine creatures.

3. Explore the Glass House Mountains: These are a series of 12 granite peaks that offer stunning views of the surrounding area. You can hike, picnic, or simply enjoy the panoramic vistas.

4. Visit the Eumundi Markets: Open on Saturdays and Wednesdays, these markets feature over 600 stalls selling art, crafts, produce, and food. It's a great place to pick up unique souvenirs and sample local delicacies.

5. Go for a scenic flight: For a truly unforgettable experience, consider taking a scenic flight over the Sunshine Coast. You'll get breathtaking views of the coastline, hinterland, and the Glass House Mountains.

6. Visit Australia Zoo: Made famous by the Crocodile Hunter, Steve Irwin, this zoo is home to a wide variety of Australian wildlife. It's a great place for families and animal lovers.

7. Enjoy local cuisine: Caloundra has a vibrant food scene with numerous restaurants offering everything from fresh seafood to international cuisines. Be sure to try some local favorites like Barramundi, Moreton Bay bugs, and mangoes.

8. Visit the Powerboat Park: If you're a fan of powerboats, this park is a must-visit. It features a museum dedicated to the history of powerboating in Australia.

9. Relax at a day spa: After a day of exploring, treat yourself to a relaxing massage or beauty treatment at one of Caloundra's many day spas.

10. Go fishing: Whether you prefer deep-sea fishing or casting a line from the shore, Caloundra offers numerous opportunities for anglers. You can even hire a charter boat if you don't have your own equipment.
*/
