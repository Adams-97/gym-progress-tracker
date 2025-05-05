package org.gym.tracker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gym.tracker.config.GymAppConfig;
import org.gym.tracker.db.IDataBase;
import org.gym.tracker.db.DataBaseFactory;
import org.gym.tracker.db.GymRecord;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

import static org.gym.tracker.config.ConfigKeys.*;

/**
 * Dictates the flow of each job
 */
public class Jobs {

    private static final Logger logger = LogManager.getLogger(Jobs.class);

    private static GymAppConfig gymAppConfig;

    Jobs() throws IOException {gymAppConfig = new GymAppConfig();}

    /**
     * This reads data from the gym excel event log and persists in db
     */
    public static void readExcel() throws IOException, SQLException {
        File excel = new File(String.valueOf(gymAppConfig.getValue(WORKBOOK_PATH)));
        List<GymRecord> excelData = null;

        try(InputStream excelInput = new FileInputStream(excel)) {

            // Insert logic here to parse data and reassign it to parsedExcel
        } catch (IOException e) {
            logger.error("Could not read excel {} successfully", excel.getAbsolutePath());
            throw e;
        }

        logger.info("Successfully retrieved GymRecords from excel");

        String dbType = String.valueOf(gymAppConfig.getValue(DB_TYPE));
        String dbUrl = String.valueOf(gymAppConfig.getValue(DB_URL));
        String gymRecordTable = String.valueOf(gymAppConfig.getValue(GYM_RECORD_TABLE));

        // Once data is retrieved then store somewhere
        IDataBase db = DataBaseFactory.getDataBase(dbType, dbUrl);
        db.insertGymRecords(excelData, gymRecordTable);
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
