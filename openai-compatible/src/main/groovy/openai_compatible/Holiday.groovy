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

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import util.Provider

// llmao, OpenRouter, and Ollama all expose an OpenAI-compatible API,
// so plain JDK HTTP + Groovy JSON is all we need
var provider = Provider.fromEnv()
println "Using $provider"

String prompt = 'What are 4 interesting things to do while I am on vacation in Caloundra?'
var body = JsonOutput.toJson([
    model: provider.model,
    messages: [[role: 'user', content: prompt]]
] + provider.extraBody)
var request = HttpRequest.newBuilder(URI.create("$provider.baseUrl/chat/completions"))
    .header('Authorization', "Bearer $provider.apiKey")
    .header('Content-Type', 'application/json')
    .POST(HttpRequest.BodyPublishers.ofString(body))
    .build()
var response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString())
var json = new JsonSlurper().parseText(response.body())
assert response.statusCode() == 200, json.error?.message
println "Response:\n" + json.choices[0].message.content
