package org.gym.tracker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public record GymRecord (
        String exercise,
        int setNumber,
        int numReps,
        LocalDate date,
        LocalTime time,
        int mesocycle,
        int programWeekNo
) {
    private static final Logger logger = LogManager.getLogger(GymRecord.class);

    /**
     * Converts row from db into a GymRecord
     */
    public static GymRecord getGymRecordFromDBResult(ResultSet rs) {
        try {
            logger.info("Attempting extraction of {} to GymRecord", rs.getRow());
            return new GymRecord(
                    rs.getString(1),
                    rs.getInt(2),
                    rs.getInt(3),
                    LocalDate.parse(rs.getString(4)),
                    LocalTime.parse(rs.getString(5)),
                    rs.getInt(6),
                    rs.getInt(7)
            );
        } catch (SQLException e) {
            logger.error("Failed in converting DB row to GymRecord");
            throw new RuntimeException(e);
        }
    }
}
