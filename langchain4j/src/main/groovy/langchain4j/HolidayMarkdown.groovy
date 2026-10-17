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
import domain.Activity
import groovy.markdown.MarkdownSlurper

// HolidayStructured asks for JSON and needs a model supporting RESPONSE_FORMAT_JSON_SCHEMA.
// Markdown is the one format every chat model is fluent in, so asking for a table and
// slurping it gets us the same records without needing structured output support.
var chatModel = OllamaChatModel.builder()
    .baseUrl("http://localhost:11434")
    .timeout(Duration.ofMinutes(5))
    .modelName("mistral:7b")
    .build()

var prompt = '''
What are 4 interesting things to do for a weekend away in Caloundra?
Reply with nothing but a Markdown table with these column headings:
Activity, Location, Day, Time
'''
var answer = chatModel.chat(prompt)
println "Response:\n$answer"

// enableTables needs org.commonmark:commonmark-ext-gfm-tables on the classpath
var tables = new MarkdownSlurper().enableTables(true).parseText(answer).tables
assert tables, 'expected the model to reply with a Markdown table'

// each row is a map keyed by the table's headings, which is all a record needs
var activities = tables.first().rows.collect {
    new Activity(it.Activity, it.Location, it.Day, it.Time)
}

println "\nActivities:\n${activities.join('\n')}"

/*
Response:
 | Activity               | Location            | Day        | Time       |
|-------------------------|----------------------|------------|------------|
| Glasshouse Mountains Hike  | Mount Tibrogargan    | Saturday   | 8:00 AM - 12:00 PM |
| Bulcock Beach Picnic     | Bulcock Beach       | Sunday     | 11:00 AM - 2:00 PM |
| visit Australia Zoo      | Australia Zoo       | Saturday   | 9:00 AM - 5:00 PM |
| Sunset Cruise            | Marina Park Caloundra    | Saturday  | 6:00 PM - 8:30 PM |

Activities:
Activity(Glasshouse Mountains Hike, Mount Tibrogargan, Saturday, 8:00 AM - 12:00 PM)
Activity(Bulcock Beach Picnic, Bulcock Beach, Sunday, 11:00 AM - 2:00 PM)
Activity(visit Australia Zoo, Australia Zoo, Saturday, 9:00 AM - 5:00 PM)
Activity(Sunset Cruise, Marina Park Caloundra, Saturday, 6:00 PM - 8:30 PM)
*/
