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
package util

import dev.langchain4j.agent.tool.ToolSpecification
import dev.langchain4j.model.chat.request.ToolChoice
import dev.langchain4j.model.chat.request.json.JsonObjectSchema
import dev.langchain4j.model.ollama.OllamaChatModel
import dev.langchain4j.model.ollama.OllamaChatRequestParameters
import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton

@Factory
class AiModelFactory {
    @Singleton
    OllamaChatModel chatModel() {
        var weatherToolSchema = JsonObjectSchema.builder()
            .addStringProperty('city', 'City name to get weather for')
            .addStringProperty('date', "Date in YYYY-MM-DD")
            .build()

        var weatherToolSpec = ToolSpecification.builder()
            .name("getWeather")
            .parameters(weatherToolSchema)
            .description('Gets the expected domain.Weather for a given city and LocalDate')
            .build()

        var weekendToolSpec = ToolSpecification.builder()
            .name("getWeekend")
            .description("Gets the LocalDate of the start of the coming weekend")
            .build()

        var params = OllamaChatRequestParameters.builder()
            .toolChoice(ToolChoice.AUTO)
            .toolSpecifications(weatherToolSpec, weekendToolSpec)
            .build()

        new CustomOllamaChatModelBuilder(params)
            .baseUrl('http://localhost:11434')
//            .logRequests(true)
//            .logResponses(true)
//                .modelName('qwen3:8b')
            .modelName('qwen3:8b')
//                .modelName('llama3.3:latest')
            .timeout(Duration.ofMinutes(5))
            .build()
    }
}
