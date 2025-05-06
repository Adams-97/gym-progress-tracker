package org.gym.tracker.spreadsheet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.util.AreaReference;
import org.gym.tracker.GymRecord;
import org.gym.tracker.config.ConfigLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ExcelStoreTest {

    private static final Logger logger = LogManager.getLogger(ExcelStoreTest.class);
    private static Map<String, String> excelConf;

    @BeforeAll
    static void setUpConf() throws IOException {
        String configLocation = "config.yml";
        URL configURL = ExcelStoreTest.class.getResource(configLocation);
        assert configURL != null;

        excelConf = ConfigLoader.getYamlConfig(configURL.getPath()).excelConfig;
    }

    @Test
    void givenAValidExcel_WhenTableIsParsed_GymRecordsRetrieved() {
        ExcelStore excelStore = new ExcelStore();
        InputStream excel = ExcelStoreTest.class.getResourceAsStream(excelConf.get("testWorkbookPath"));
        assert excel != null;

        AreaReference tableArea = excelStore.getTableAreaReference(
                excel, excelConf.get("validTableWorksheet"), excelConf.get("validTableName")
        );
        List<Integer> rows = excelStore.getRowIndexes(tableArea);
        List<Integer> cols = excelStore.getColIndexes(tableArea);
        List<GymRecord> parsedRows = excelStore.parseTable(rows, cols);

        assertEquals(3, parsedRows.size());
        GymRecord lastRecord = new GymRecord(
            3,
            "exercise3",
            3,
            3,
            LocalDate.of(2025,2,3),
            LocalTime.of(16, 30),
            3,
            3
        );
        assertEquals(lastRecord, parsedRows.getLast());
    }
}
