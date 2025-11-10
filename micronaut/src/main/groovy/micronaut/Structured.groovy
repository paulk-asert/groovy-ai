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
package micronaut

import dev.langchain4j.service.SystemMessage
import domain.Itinerary
import io.micronaut.context.ApplicationContext
import io.micronaut.langchain4j.annotation.AiService

@AiService
interface HolidayBot {
    @SystemMessage('''
    Return holiday activity suggestions as structured JSON matching Itinerary.
    Timebox activities if needed to fit within the holiday length and not overlap other
    activities while still giving enough time to see all major aspects of each attraction.
    Exclude other information.
    ''')
    Itinerary itinerary(String userMessage)
}

try(var context = ApplicationContext.run()) {
    println context.getBean(HolidayBot)
        .itinerary('Four great things to see in Auckland over a weekend.')
        .itinerary
        .join('\n')
}

/*
Activity(Visit Auckland War Memorial Museum, Auckland, Day 1, 9:00 AM - 5:00 PM)
Activity(Explore Viaduct Harbour and Wynyard Quarter, Auckland, Day 1, 6:00 PM - 8:00 PM)
Activity(Hike up Mount Eden, Auckland, Day 2, 9:00 AM - 12:00 PM)
Activity(Visit Waiheke Island and its vineyards, Waiheke Island, Day 2, 1:00 PM - 6:00 PM)
*/
