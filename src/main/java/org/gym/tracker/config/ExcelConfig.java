package org.gym.tracker.config;

public record ExcelConfig (String workbookName,
        String logWorksheetIndex,
        String logTableName
) {
}
