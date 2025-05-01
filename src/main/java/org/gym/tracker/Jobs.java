package org.gym.tracker;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gym.tracker.config.ConfigLoader;
import org.gym.tracker.config.YamlConfig;
import org.gym.tracker.db.ExcelStore;
import org.gym.tracker.db.GymEvent;

import java.io.*;
import java.util.List;
import java.util.Map;

import static org.gym.tracker.config.YamlConfig.WORKBOOK_PATH;

/**
 * Dictates the flow of each job
 */
public class Jobs {

    private static final Logger logger = LogManager.getLogger(Jobs.class);
    public static Dotenv envVar;
    public static YamlConfig yaml;

    Jobs() throws FileNotFoundException {
        if (!Boolean.parseBoolean(System.getenv("DEFAULT_ENV"))) {
            envVar = ConfigLoader.getDotEnv(System.getenv("DOTENV_PATH"));
        } else envVar = ConfigLoader.getDotEnv();

        try {
            if (!Boolean.parseBoolean(envVar.get("DEFAULT_CONF"))) {
                yaml = ConfigLoader.getYamlConfig(envVar.get("CONFIG_LOCATION"));
            } else yaml = ConfigLoader.getYamlConfig();
        } catch (FileNotFoundException e) {
            logger.error("Cannot find yaml config file at initialisation");
            throw e;
        }
    }

    /**
     * This reads data from the gym excel event log and persists in db
     */
    public static void readExcel() throws IOException {
        Map<String, String> readExcelConf = yaml.excelConfig;
        File excel = new File(readExcelConf.get(WORKBOOK_PATH));

        List<GymEvent> parsedExcel;
        try(InputStream excelInput = new FileInputStream(excel)) {

            // Insert logic here to parse data and reassign it to parsedExcel
        } catch (IOException e) {
            logger.error("Could not read excel {} successfully", excel.getAbsolutePath());
            throw e;
        }

        // Once data is retrieved then store somewhere
    }

    public static void calcExercises() {
        // ????
    }

    public static void export() {
        // ????
    }

}
