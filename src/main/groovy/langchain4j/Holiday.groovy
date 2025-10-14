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

var chatModel = OllamaChatModel.builder()
    .baseUrl("http://localhost:11434")
    .timeout(Duration.ofMinutes(5))
    .modelName("mistral:7b")
    .build()

String prompt = 'What are 4 interesting things to do while I am on vacation in Caloundra?'
println "Response:\n" + chatModel.chat(prompt)

/*
Response:
 1. Visit the beautiful beaches: Caloundra is known for its stunning beaches, with Kings Beach and Moffat Beach being some of the most popular ones. You can spend your days sunbathing, swimming, or surfing.

2. Explore the underwater world: Take a trip to the UnderWater World Sea Life Mooloolaba, an aquarium that houses a variety of marine life including sharks, turtles, and seahorses. It's a great way to learn about and appreciate the ocean's wonders.

3. Visit the Glastonbury Estate: This historic homestead offers a glimpse into Australia's past. The estate features beautiful gardens, a tea room, and often hosts various events throughout the year.

4. Take a day trip to the Glass House Mountains: Just a short drive from Caloundra, these iconic volcanic plugs offer breathtaking views and hiking trails for all levels of fitness. You can also visit the Kondalilla National Park for waterfalls and rainforest walks.
*/
