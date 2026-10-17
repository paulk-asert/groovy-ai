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

/**
 * Picks an OpenAI-compatible endpoint based on which API key is set:
 * llmao (the ASF LLM gateway), then OpenRouter, falling back to local Ollama.
 */
record Provider(String name, String baseUrl, String apiKey, String model, Map<String, Object> extraBody) {
    /** The preferred provider: the first one configured. */
    static Provider fromEnv() {
        allFromEnv().first()
    }

    /**
     * Every configured hosted provider, for asking them all the same question.
     * Local Ollama is only the fallback when no hosted key is set, since CI runs
     * the hosted examples without an Ollama server.
     */
    static List<Provider> allFromEnv() {
        var env = System.getenv()
        List<Provider> hosted = []
        if (env.LLMAO_API_KEY) {
            // Qwen3 reasons by default, which can use up the output budget and return an empty answer
            hosted << new Provider('llmao', 'https://llm.apache.org/v1', env.LLMAO_API_KEY,
                env.LLMAO_MODEL ?: 'qwen3.8-27b', [chat_template_kwargs: [enable_thinking: false]])
        }
        if (env.OPENROUTER_API_KEY) {
            hosted << new Provider('OpenRouter', 'https://openrouter.ai/api/v1', env.OPENROUTER_API_KEY,
                env.OPENROUTER_MODEL ?: 'mistralai/mistral-small-3.2-24b-instruct', [:])
        }
        hosted ?: [new Provider('Ollama', 'http://localhost:11434/v1', 'ollama', 'mistral:7b', [:])]
    }

    String toString() { "$model via $name ($baseUrl)" }
}
