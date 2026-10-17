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

import dev.langchain4j.model.decision.request.ChoiceQuestion
import dev.langchain4j.model.decision.request.DecisionRequest
import dev.langchain4j.model.decision.request.ScaleQuestion
import dev.langchain4j.model.decision.request.YesNoQuestion
import dev.langchain4j.model.typesafe.TypeSafeDecisionModel
import domain.Weather

// A decision model is not a chat model: one request carries a state and several named,
// typed questions, and each answer comes back with probabilities and a calibrated
// confidence. Ollama 0.35+ serves this "System One" API at /v1/systemone, which is
// exactly where langchain4j-typesafe points by default, so a local decision model
// needs no extra configuration. Requires: ollama pull tev1
var decisionModel = TypeSafeDecisionModel.builder()
    .baseUrl('http://localhost:11434')
    .apiKey('ollama')  // local Ollama ignores this, but the builder expects a key
    .modelName('tev1') // nimble, clef and clef-flash are also decision models
    .timeout(Duration.ofMinutes(5))
    .build()

var saturday = new Weather('Sunny and Hot', 30, 37)
var sunday = new Weather('Raining', 5, 15)

var activities = [
    beach  : 'Swimming and sunbathing at Kings Beach',
    gallery: 'Browsing the Caloundra Regional Gallery',
    hike   : 'A Glass House Mountains summit hike',
]

// the state can be text or JSON; the questions are answered in parallel against it
var response = decisionModel.decide(DecisionRequest.builder()
    // toString() matters: the validator accepts String but not Groovy's GString
    .input([saturday: saturday.toString(), sunday: sunday.toString()])
    .question('beachDay', YesNoQuestion.of('Is the Saturday weather good for the beach?'))
    .question('sundayPick', ChoiceQuestion.of('Which activity suits the Sunday weather best?', activities))
    // score criteria must be plain strings ordered lowest to highest
    .question('effort', ScaleQuestion.of('How much physical effort does a summit hike need?',
        ['Easy: a flat stroll', 'Moderate: some walking and climbing', 'Hard: a steep summit climb']))
    .build())

var beachDay = response.yesNo('beachDay')
var pick = response.choice('sundayPick')
var effort = response.scale('effort')

println """
Beach on Saturday?  ${beachDay.isYes(0.5) ? 'yes' : 'no'} (probability ${beachDay.probability().round(3)})
Pick for Sunday?    ${pick.value()}  "${activities[pick.value()]}"
                    ${pick.probabilities().collect { k, v -> "$k=${v.round(3)}" }.join(', ')}
                    confidence ${pick.confidence().round(3)}
Effort for a hike?  ${effort.mean().round(2)} on a 0..2 scale
Tokens used:        ${response.tokenUsage().inputTokenCount()} in, ${response.tokenUsage().outputTokenCount()} out
"""

/*
Beach on Saturday?  yes (probability 0.687)
Pick for Sunday?    gallery  "Browsing the Caloundra Regional Gallery"
                    beach=0.001, gallery=0.993, hike=0.006
                    confidence 0.958
Effort for a hike?  1.72 on a 0..2 scale
Tokens used:        496 in, 0 out
*/
