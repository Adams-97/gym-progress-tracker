package org.gym.tracker;

import java.time.LocalDate;
import java.time.LocalTime;

public record GymRecord (
        int rowId,
        String exercise,
        int setNumber,
        int numReps,
        LocalDate date,
        LocalTime time,
        int mesocycle,
        int programWeekNo
) {
}
