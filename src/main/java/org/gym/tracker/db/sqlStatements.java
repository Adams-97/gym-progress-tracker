package org.gym.tracker.db;

import java.util.ArrayList;
import java.util.List;

public class sqlStatements {

    public static final List<String> GYM_RECORD_COLS = getGymRecordCols();
    public static final List<String> GYM_RECORD_COLS_MINUS_INDEX = GYM_RECORD_COLS.stream()
            .filter(colName -> !colName.toLowerCase().contains("_id"))
            .toList();

    /**
     * Generates an insert statement with ?'s for cols and insert values
     */
    public static String getInsertPrepStmntSql(String tableName, List<String> insertCols, int numInsertValues) {
        if (insertCols.size() != numInsertValues) {
            throw new IllegalArgumentException(
                String.format("Number of insert columns (%s) must match insert values (%s)",
                    insertCols.size(), numInsertValues)
            );
        }
        String concatInsertCols = "(" + String.join(",",insertCols) + ")";
        String repeatParameterString = "?,".repeat(numInsertValues);
        // Removes final trailing comma so ?) instead of ?,)
        var insertValues = repeatParameterString.substring(0, repeatParameterString.length()-1);

        return String.format("INSERT INTO %s %s VALUES (%s);", tableName, concatInsertCols, insertValues);
    }

    /**
     * Gets the parameterised sql query for the GymRecord transactional table
     */
    public static String getGymRecordInsertSql(String tableName) {
        return getInsertPrepStmntSql(tableName, GYM_RECORD_COLS_MINUS_INDEX, GYM_RECORD_COLS_MINUS_INDEX.size());
    }

    /**
     * Generates a generic sql statement for select query based on columns given
     */
    public static String getSelectSql(String tableName, List<String> cols) {
        return String.format("SELECT %s FROM %s", String.join(",",cols), tableName);
    }

    /**
     * Gets sql query to view the GymRecord transactional table. row_id included
     */
    public static String getGymRecordSelectAll(String tableName) {
        return getSelectSql(tableName, GYM_RECORD_COLS);
    }

    /**
     * Gets sql query to view the GymRecord transactional table. row_id not included
     */
    public static String getGymRecordSelectAllNonIndex(String tableName) {
        return getSelectSql(tableName, GYM_RECORD_COLS_MINUS_INDEX);
    }

    /**
     * Used for setting GymRecord transactional table columns
     */
    private static List<String> getGymRecordCols() {
        var cols = new ArrayList<String>();
        cols.add("LOG_ID");
        cols.add("EXERCISE");
        cols.add("SET_NO");
        cols.add("NUM_REPS");
        cols.add("DATE_REGISTERED");
        cols.add("TIME_REGISTERED");
        cols.add("MESOCYCLE");
        cols.add("PROGRAM_WEEK_NO");
        return cols;
    }
}