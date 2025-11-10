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

import dev.langchain4j.service.SystemMessage
import domain.Itinerary
import io.micronaut.langchain4j.annotation.AiService

@AiService(tools = [WeatherTool, WeekendTool])
interface WeatherAwareBot {
    @SystemMessage('''
    You are a travel planning assistant that helps users plan activities for their holidays.
    Before finalizing the itinerary, use the WeatherTool tool for each destination city and each day of the itinerary.
    Use the WeatherTool by creating a JSON object:
    { "tool": "getWeather", "arguments": { "city": "...", "date": "YYYY-MM-DD" } }
    Only after getting results from the tool, produce final Itinerary JSON.
    Use the WeekendTool to work out the date for "the coming weekend".
    Use the tool information to adjust your activity suggestions accordingly.
    Return only an Itinerary JSON structure.
    ''')
    Itinerary chat(String userMessage)
}
