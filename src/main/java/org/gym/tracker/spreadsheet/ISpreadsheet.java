package org.gym.tracker.spreadsheet;

import org.apache.poi.ss.util.AreaReference;
import org.gym.tracker.GymRecord;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

interface ISpreadsheet {

    /**
     * Use the input stream and other parameters to get an AreaReference from target table
     */
    AreaReference getTableAreaReference(InputStream excelFile, String sheetName, String tableName);

    /**
     * Get all index positions of rows
     */
    List<Integer> getColIndexes(AreaReference tableArea);

    /**
     * Get all index positions of columns
     */
    List<Integer> getRowIndexes(AreaReference tableArea);

    /**
     * Use both indexes to iterate through the table and parse out GymRecords
     */
    List<GymRecord> parseTable(List<Integer> rowIndexes, List<Integer> colIndexes);

    /**
     * Given a gymRecord it writes to the datasource and returns 1 if it wrote successfully
     */
    int writeLineOfGymData(GymRecord row);

    /**
     * Performs writes across list, returning number of successful writes
     */
    default List<Integer> writeLinesOfGymData(List<GymRecord> rows) {
        return rows.stream().map(this::writeLineOfGymData).collect(Collectors.toList());
    }

}
