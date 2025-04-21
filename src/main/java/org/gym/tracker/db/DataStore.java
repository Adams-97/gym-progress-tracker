package org.gym.tracker.db;

import java.util.List;
import java.util.stream.Collectors;

interface DataStore {

    /**
     * Converts a row from table to a GymRecord
     */
    GymRecord parseLineOfGymData(List<String> lineFromStore);

    /**
     * Given a gymRecord it writes to the datasource and returns 1 if it wrote successfully
     */
    int parseLineOfGymData(GymRecord row);

    /**
     * Performs writes across list, returning number of successful writes
     */
    default List<Integer> parseLinesOfGymData(List<GymRecord> rows) {
        return rows.stream().map(this::parseLineOfGymData).collect(Collectors.toList());
    }

}
