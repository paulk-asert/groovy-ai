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
package openai_compatible

import groovy.http.Body
import groovy.http.HttpBuilderClient
import groovy.http.Post
import util.Provider

// llmao, OpenRouter, and Ollama all expose an OpenAI-compatible API,
// so Groovy's declarative HTTP client is all we need: the interface IS the client
@HttpBuilderClient('http://localhost:11434/v1')
interface ChatApi {
    @Post('chat/completions')
    Map chatCompletion(@Body Map request)
}

var provider = Provider.fromEnv()
println "Using $provider"

// the base URL needs its trailing slash, otherwise resolving 'chat/completions'
// against it would discard the provider's path (e.g. the '/v1' in Ollama's URL)
var api = ChatApi.create {
    baseUri "$provider.baseUrl/"
    header 'Authorization', "Bearer $provider.apiKey"
    requestTimeout Duration.ofMinutes(5)
}

String prompt = 'What are 4 interesting things to do while I am on vacation in Caloundra?'
// the request body is serialized as JSON, the response parsed back into a Map,
// and any 4xx/5xx becomes an exception carrying the provider's error message
var json = api.chatCompletion([
    model: provider.model,
    messages: [[role: 'user', content: prompt]]
] + provider.extraBody)

println "Response:\n" + json.choices[0].message.content
