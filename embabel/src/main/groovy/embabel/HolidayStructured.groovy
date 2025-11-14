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
package embabel

import com.embabel.agent.api.common.OperationContext
import com.embabel.agent.config.annotation.EnableAgents
import com.embabel.agent.config.annotation.LoggingThemes
import domain.Itinerary
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
@EnableAgents(loggingTheme = LoggingThemes.STAR_WARS)
void main() {
    try(var context = SpringApplication.run(HolidayStructured)) {
        println context.getBean(OperationContext)
            .ai()
            .withDefaultLlm()
            .createObject('What are some interesting things to do while I am on vacation in Caloundra?', Itinerary)
            .display()
    }
}
/*
Activity(Visit the Kings Beach, Kings Beach, Caloundra, Day 1, Morning)
Activity(Explore Bulcock Beach, Bulcock Beach, Caloundra, Day 1, Afternoon)
Activity(Dine at Mooloolaba Seafood Market, Mooloolaba Seafood Market, Mooloolaba, Day 1, Evening)
Activity(Visit the Aussie World Theme Park, Aussie World, Palmview, Day 2, Whole day)
Activity(Relax at Shelly Beach, Shelly Beach, Caloundra, Day 3, Morning)
Activity(Try Surfing at Currimundi Beach, Currimundi Beach, Currimundi, Day 3, Afternoon)
Activity(Explore the Eumundi Markets, Eumundi Markets, Eumundi, Day 4, Morning to Afternoon)
*/
