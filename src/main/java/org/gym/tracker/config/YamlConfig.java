package org.gym.tracker.config;

import java.util.Map;

public class YamlConfig {

    public Map<String, String> excelConfig;

    //  Excel config constants
    public static final String WORKBOOK_PATH = "workbookPath";
    public static final String WORKBOOK_NAME = "workbookName";
    public static final String WORKSHEET_NAME = "logWorksheetName";
    public static final String LOG_TABLE_NAME = "workoutLog";
}