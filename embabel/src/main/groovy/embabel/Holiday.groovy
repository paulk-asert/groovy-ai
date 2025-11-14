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
            .generateText('What are four interesting things to do while I am on vacation in Caloundra?')
    }
}

/*
 1. Visit the Bulcock Beach: This is a popular beach in Caloundra, perfect for swimming, sunbathing, and enjoying various water sports. There's also a picturesque esplanade with cafes, shops, and art galleries nearby.

2. Explore the Kings Beach Park: Located next to Kings Beach, this park offers a variety of facilities including picnic areas, BBQ facilities, playgrounds, and beautiful views of the ocean. It's a great spot for families with children.

3. Visit the Australian Zoo: Made famous by Steve Irwin, the Australian Zoo is just a short drive from Caloundra. Here you can see a wide variety of Australian wildlife, including kangaroos, koalas, and crocodiles.

4. Take a day trip to the Glass House Mountains: These are a series of 13 steep sided, volcanic plugs that dominate the local landscape. You can hike some of the mountains, or simply enjoy their unique beauty from various lookout points. Some popular ones include Mount Ngungun and Mount Beerwah.
*/
