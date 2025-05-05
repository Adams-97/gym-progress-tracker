package org.gym.tracker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gym.tracker.config.GymAppConfig;

import java.io.*;

import static org.gym.tracker.config.ConfigKeys.WORKBOOK_PATH;

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
    public static void readExcel() throws IOException {
        File excel = new File(String.valueOf(gymAppConfig.get(WORKBOOK_PATH)));

//        List<GymEvent> parsedExcel;
        try(InputStream excelInput = new FileInputStream(excel)) {

            // Insert logic here to parse data and reassign it to parsedExcel
        } catch (IOException e) {
            logger.error("Could not read excel {} successfully", excel.getAbsolutePath());
            throw e;
        }

        // Once data is retrieved then store somewhere
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
