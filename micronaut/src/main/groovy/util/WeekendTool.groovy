package util

import dev.langchain4j.agent.tool.Tool
import io.micronaut.context.annotation.Executable
import jakarta.inject.Singleton

import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

@Singleton
class WeekendTool {
    @Tool("The date of the coming weekend in YYYY-MM-DD format")
//    @Executable
    String getWeekend() {
        LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY))
    }
}
