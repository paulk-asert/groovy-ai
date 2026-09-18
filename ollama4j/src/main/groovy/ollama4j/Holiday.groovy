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
import io.github.ollama4j.models.chat.OllamaChatRequest

var ollama = new Ollama(requestTimeoutSeconds: 300)
println "Found ollama: ${ollama.ping()}"

var prompt = 'What are 4 interesting things to do while I am on vacation in Glasgow?'
var builder = OllamaChatRequest.builder()
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
 1. Visit the Glasgow Cathedral: This historic cathedral is a must-see in Glasgow. It's one of the 5 great medieval churches
 of Scotland and offers a glimpse into the city's rich history. The adjoining Necropolis cemetery also provides stunning views of the city.

2. Explore the Kelvingrove Art Gallery and Museum: This world-class museum houses over 8,000 objects, including works by
artists like Botticelli, Monet, and Rembrandt. It's a great place to spend a day learning about art, history, and science.

3. Stroll through Glasgow Green Park: One of the city's oldest parks, Glasgow Green offers beautiful landscapes,
a bandstand, a skate park, and even a mini-golf course. It's a perfect spot for a picnic or a leisurely walk.

4. Take a tour of The Tenement House: This well-preserved Victorian dwelling provides a unique insight into the lives of
Glasgow's middle class during the early 20th century. The guided tours are informative and engaging, offering a glimpse into a bygone era.

Best thing:
Given that you have only half a day and want to see a mix of history, culture, and local life, I would recommend visiting
the Kelvingrove Art Gallery and Museum. It's centrally located, easily accessible, and offers an extensive collection of
art and artifacts that provide a fascinating glimpse into Glasgow's past and present. Plus, it's free to enter, making it
a great value for your time.
If you prefer outdoor activities or want to experience the local vibe, Glasgow Green Park is another excellent choice.
You can take a leisurely stroll through the park, enjoy the scenery, and even grab a bite at one of the nearby cafes or food trucks.

*/
