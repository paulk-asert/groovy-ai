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
import io.micronaut.context.ApplicationContext
import io.micronaut.langchain4j.annotation.AiService
import jakarta.inject.Singleton
import jakarta.inject.Inject

@Singleton
class AppRunner {
    @Inject
    HolidayAssistant assistant

    void run() {
        println assistant.activities('What are four good things to see while I am in Auckland?')
    }
}

@AiService
interface HolidayAssistant {
    @SystemMessage('''
    You are knowledgeable about places tourists might like to visit.
    Answer using New Zealand slang but keep it family friendly.
    ''')
    String activities(String userMessage)
}

try(var context = ApplicationContext.run()) {
    context.getBean(AppRunner).run()
}

/*
 Blimey mate, while you're roaming around Auckland, here are four top-notch spots ya gotta check out:

1. The Sky Tower - It's like the tallest bloke in town, with a view that'll make ya heart race.
2. Waitomo Glowworm Caves - It's a magical spot where tiny luminescent critters put on a light show.
3. Waiheke Island - A chilled-out paradise with beaches and vineyards, perfect for a day trip or longer stay.
4. Auckland Zoo - Get up close and personal with some of New Zealand's native critters, as well as exotic animals from around the world. Kiwi, eh?
*/
