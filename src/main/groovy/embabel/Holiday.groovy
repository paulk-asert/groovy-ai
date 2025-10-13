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
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
@EnableAgents(loggingTheme = LoggingThemes.STAR_WARS)
void main() {
    try(var context = SpringApplication.run(Holiday)) {
        println context.getBean(OperationContext)
            .ai()
            .withDefaultLlm()
            .generateText('What are some interesting things to do while I am on vacation in Caloundra?')
    }
}

/*
Caloundra, located on the Sunshine Coast in Queensland, Australia, offers a variety of activities for tourists. Here are some suggestions for an enjoyable vacation:

1. Visit the beautiful beaches: Caloundra has several beautiful beaches, such as Kings Beach, Shelly Beach, and Moffat Beach, where you can swim, sunbathe, or surf.

2. Explore the Coastal Walk: Take a leisurely stroll along the Coastal Walk, which offers stunning views of the ocean, coastal cliffs, and nearby islands.

3. Visit the Underwater World SEA LIFE Mooloolaba Aquarium: Discover an amazing underwater world filled with sea turtles, sharks, seahorses, and more.

4. Spend a day at Australia Zoo: Home to over 1,200 animals and the late Steve Irwin's family, this iconic zoo offers up-close encounters with some of Australia's most famous wildlife.

5. Visit the Glastonbury Estate Winery: Taste locally produced wines at this picturesque winery, which also features a restaurant and beautiful gardens.

6. Explore the Bulcock Beach Esplanade: This vibrant area offers a variety of shops, cafes, and restaurants, as well as regular markets on weekends.

7. Take a day trip to Fraser Island: Known for its stunning beaches, crystal-clear waters, and rainforests, Fraser Island is just a short boat ride away from Caloundra.

8. Enjoy the Pumicestone Passage: Go boating, kayaking, or fishing in this beautiful waterway that separates Bribie Island from the mainland.

9. Visit the Powerboat Park: Watch high-speed powerboats compete in various races at this popular watersports venue.

10. Relax at a spa or wellness center: Pamper yourself with a massage, beauty treatment, or yoga class at one of Caloundra's many wellness centers.

*/
