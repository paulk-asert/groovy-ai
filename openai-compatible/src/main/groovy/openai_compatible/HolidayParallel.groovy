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

import groovy.concurrent.ParallelScope
import groovy.http.Body
import groovy.http.HttpBuilderClient
import groovy.http.Post
import util.Provider

// one declarative client, instantiated once per provider (see Holiday.groovy for the basics)
@HttpBuilderClient('http://localhost:11434/v1')
interface CompareApi {
    @Post('chat/completions')
    Map chatCompletion(@Body Map request)
}

var providers = Provider.allFromEnv()
println "Asking ${providers.size()} provider(s): ${providers*.name.join(', ')}"

var prompt = 'Name one must-see attraction in Caloundra and say why in one sentence.'

var ask = { Provider provider ->
    var api = CompareApi.create {
        baseUri "$provider.baseUrl/"
        header 'Authorization', "Bearer $provider.apiKey"
        requestTimeout Duration.ofMinutes(5)
    }
    try {
        api.chatCompletion([
            model: provider.model,
            messages: [[role: 'user', content: prompt]]
        ] + provider.extraBody).choices[0].message.content.trim()
    } catch (RuntimeException e) {
        // a 4xx/5xx from one provider shouldn't lose the other answers
        "<no answer: $e.message>"
    }
}

// each provider is a separate endpoint, so these calls overlap instead of queueing;
// withPool keeps the blocking calls off the CPU-sized common pool
var parallel = System.timed {
    ParallelScope.withPool(providers.size()) {
        providers.collectParallel(ask)
    }
}
printf 'all %d in parallel: %,d ms%n', providers.size(), parallel.millis

// the same work one provider at a time, to show what the fan-out saved
if (providers.size() > 1) {
    var sequential = System.timed { providers.collect(ask) }
    printf 'one at a time    : %,d ms%n', sequential.millis
}

// collectParallel preserves order, so answers still line up with their providers
[providers, parallel.result].transpose().each { provider, answer ->
    println "\n$provider.name ($provider.model):\n  $answer"
}
