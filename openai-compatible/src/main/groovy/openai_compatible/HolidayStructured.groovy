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

import dev.langchain4j.memory.chat.MessageWindowChatMemory
import dev.langchain4j.model.openai.OpenAiChatModel
import dev.langchain4j.model.openai.OpenAiChatRequestParameters
import dev.langchain4j.service.AiServices
import domain.Activity
import util.Provider

import static dev.langchain4j.model.chat.Capability.RESPONSE_FORMAT_JSON_SCHEMA

interface HolidayBot {
    List<Activity> extractActivitiesFrom(String text)
}

// LangChain4j's OpenAI client works with llmao, OpenRouter, and Ollama by changing the base URL;
// the chosen model must support structured outputs
var provider = Provider.fromEnv()
println "Using $provider"

var model = OpenAiChatModel.builder()
    .baseUrl(provider.baseUrl)
    .apiKey(provider.apiKey)
    .modelName(provider.model)
    .defaultRequestParameters(OpenAiChatRequestParameters.builder()
        .customParameters(provider.extraBody)
        .build())
    .supportedCapabilities(RESPONSE_FORMAT_JSON_SCHEMA)
    .strictJsonSchema(true)
    .timeout(Duration.ofMinutes(5))
    .build()

var chatMemory = MessageWindowChatMemory.withMaxMessages(10)

var bot = AiServices.builder(HolidayBot)
    .chatModel(model)
    .chatMemory(chatMemory)
    .build()

var prompt = '''
What are 4 interesting things to do for a weekend away in Caloundra?
Return holiday activity suggestions as structured JSON matching Itinerary.
Give a description, location, day of weekend, and suggested time of day for each of the four activities.
'''
var response = bot.extractActivitiesFrom(prompt)

var prompt2 = '''
If my only spare time is Sunday morning, and I can only go to one activity, which would you recommend?
'''
var response2 = bot.extractActivitiesFrom(prompt2)

println """
Four things:
${response.join('\n')}

Best thing:
${response2.join('\n')}
"""
