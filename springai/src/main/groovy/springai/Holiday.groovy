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
package springai

import org.springframework.ai.chat.client.ChatClient
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
void main() {
    try(var context = SpringApplication.run(Holiday)) {
        var chatClient = context.getBean(ChatClient.Builder).build()
        println chatClient
                .prompt("What are four interesting things to do while I am on vacation in Caloundra?")
                .call()
                .content()
    }
}
/*
 1. Visit the beautiful beaches: Caloundra is known for its stunning beaches, with some of the most popular ones being Kings Beach, Shelly Beach, and Moffat Beach. These beaches offer a variety of activities such as swimming, surfing, fishing, and picnicking.

2. Explore the Underwater World Sea Life: This is an aquarium located at the Caloundra Road, Caloundra. It's home to thousands of marine animals including sharks, turtles, seahorses, and stingrays. You can also enjoy interactive experiences like feeding the seals or diving with sharks.

3. Visit the Bulcock Beach Esplanade: This is a vibrant hub of activity in Caloundra. It's lined with cafes, restaurants, shops, and art galleries. On weekends, it hosts a local market selling fresh produce, arts, crafts, and food.

4. Take a day trip to the Glass House Mountains: Just a short drive from Caloundra, these 12 distinctive rock formations offer breathtaking views of the surrounding landscape. You can hike up some of the mountains or simply enjoy the panoramic vistas from the base. Some popular ones to visit are Mount Beerwah and Mount Coolum.
*/
