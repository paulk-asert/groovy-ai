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

var ollama = new Ollama(requestTimeoutSeconds: 300)
println "Found ollama: ${ollama.ping()}"

var prompt = 'What are 4 interesting things to do while I am on vacation in Caloundra?'
var builder = OllamaChatRequestBuilder.builder()
    .withModel('mistral:7b')

var request = builder
    .withMessage(OllamaChatMessageRole.USER, prompt)
    .build()

var result = ollama.chat(request, null)
println "Four things:\n$result.responseModel.message.response"

var prompt2 = 'If I had half a day and can only go to one, which would you recommend?'
request = builder
    .withMessages(result.chatHistory)
    .withMessage(OllamaChatMessageRole.USER, prompt2)
    .build()

result = ollama.chat(request, null)
println "Best thing:\n$result.responseModel.message.response"

/*
Found ollama: true

Four things:
 1. Visit the beautiful beaches: Caloundra is famous for its stunning beaches, including Kings Beach, Moffat Beach, and Bulcock Beach. Spend your days soaking up the sun, swimming, or surfing.

2. Explore the UnderWater World SeaLife Aquarium: This marine attraction offers a unique opportunity to interact with various sea creatures. You can even have a close encounter with sharks and turtles!

3. Visit the Glastonbury Estate: For those who love history, this beautiful estate is worth a visit. It was built in the 1920s and features a variety of artifacts and memorabilia from World War I and II.

4. Take a day trip to the Australia Zoo: Made famous by the Crocodile Hunter, Steve Irwin, the Australia Zoo is just a short drive from Caloundra. It's home to a wide variety of Australian wildlife, including kangaroos, koalas, and crocodiles. Don't miss the daily wildlife shows!

Best thing:
 If you only have half a day and can choose just one activity, I would recommend visiting the beautiful Kings Beach in Caloundra. It offers a lovely stretch of sandy beach, perfect for swimming, sunbathing, or simply taking a leisurely stroll along the shoreline. The beach also has various amenities like picnic areas, BBQ facilities, and a playground for children. Plus, it provides stunning views of the Pacific Ocean and the Glass House Mountains in the distance. It's the perfect place to relax and soak up the sun on your vacation!

*/
