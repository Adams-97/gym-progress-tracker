package org.gym.tracker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gym.tracker.config.GymAppConfig;
import org.gym.tracker.db.IDataBase;
import org.gym.tracker.db.DataBaseFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.gym.tracker.config.ConfigKeys.*;
import static org.gym.tracker.db.sqlStatements.getGymRecordInsertSql;

/**
 * Dictates the flow of each job
 */
public class Jobs {

    private static final Logger logger = LogManager.getLogger(Jobs.class);

    private static GymAppConfig gymAppConfig;

    Jobs() {gymAppConfig = new GymAppConfig();}

    /**
     * This reads data from the gym excel event log and persists in db
     */
    public static void readExcel() {
        Path excel = Paths.get(String.valueOf(gymAppConfig.getValue(WORKBOOK_PATH)));
        List<GymRecord> excelData = null;

        try(InputStream excelInput = Files.newInputStream(excel)) {
            // Insert logic here to parse data and reassign it to parsedExcel


            logger.info("Successfully retrieved GymRecords from excel");

            // Once data is retrieved then store somewhere
            String gymLogInsertQuery = getGymRecordInsertSql((String) gymAppConfig.getValue(GYM_RECORD_TABLE));
            IDataBase db = DataBaseFactory.getDataBase(gymAppConfig);
            db.insertGymRecords(excelData, gymLogInsertQuery);

        } catch (IOException e) {
            logger.error("Could not read excel {} successfully", excel);
            throw new RuntimeException(e);
        }
    }

    /**
     * Calculates next lot of exercises and writes to db
     */
    public static void calcExercises() {
        // ????
    }

    /**
     * Exports data from db
     */
    public static void exportData() {
        // ????
    }

}
