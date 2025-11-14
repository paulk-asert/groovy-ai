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

import domain.Itinerary
import org.springframework.ai.chat.client.ChatClient
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
void main() {
    try(var context = SpringApplication.run(Holiday)) {
        var chatClient = context.getBean(ChatClient.Builder).build()
        var response = chatClient
                .prompt("What are some interesting things to do while over a long weekend in Caloundra?")
                .call()
                .entity(Itinerary)
        println "Response:\n" + response.display()
    }
}
/*
Response:
Activity(Visit Kings Beach, Caloundra, Day 1, Morning)
Activity(Explore Bulcock Beach, Caloundra, Day 1, Afternoon)
Activity(Sunset at Moffat Headland, Caloundra, Day 1, Evening)
Activity(Visit the Australian Zoo, Beerwah, Day 2, Whole Day)
Activity(Relax at Shelly Beach, Caloundra, Day 3, Morning)
Activity(Explore Pumicestone Passage by boat tour, Caloundra, Day 3, Afternoon)
*/
