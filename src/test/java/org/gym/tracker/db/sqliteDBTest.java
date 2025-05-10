package org.gym.tracker.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gym.tracker.GymRecord;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.gym.tracker.db.sqlStatements.getGymRecordInsertSql;
import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class sqliteDBTest {

    private static final Logger logger = LogManager.getLogger(sqliteDBTest.class);
    private static sqliteDb db;
    private static Path sqlFile;
    private static String testTableName;

    @BeforeAll
    static void getTestSqlite() throws URISyntaxException {
        String testSqliteLocation = "src/test/resources/test.db";
        URL testSqlFile = sqliteDBTest.class.getResource("/create_test_db.sql");
        assert testSqlFile != null;

        db = new sqliteDb("jdbc:sqlite:" + testSqliteLocation);
        sqlFile = Paths.get(testSqlFile.toURI());
        testTableName = "TEST_GYM_LOG";
    }

    @BeforeEach
    void resetDb() {
        try {
            if (Files.deleteIfExists(db.filePath)) {
                logger.info("Deleted existing test sqlite file");
            } else {logger.debug("No existing sqlite file found");}
        } catch (IOException e) {
            logger.error("Error when trying to delete existing db");
            throw new RuntimeException(e);
        }
    }

    @Test
    void givenValidSqlScript_WhenScriptIsRead_ThenSqlQueriesParsed() {
        Path readSqlTestFile = Paths.get("src/test/resources/test_read_sql.sql");
        List<String> expSqlQueries = new ArrayList<>();
        expSqlQueries.add("SELECT 1,2,3,4,5;");
        expSqlQueries.add("CREATE TABLE TEST_TABLE (A TEXT, B INTEGER);");
        assertEquals(expSqlQueries, db.readSqlScript(readSqlTestFile));
    }

    @Test
    void givenInvalidSqlScript_WhenScriptIsRead_ThenFileNotFoundThrown() {
        Path readSqlTestFile = Paths.get("Wrong_path");
        assertThrows(RuntimeException.class, () -> db.readSqlScript(readSqlTestFile));
    }

    @Test
    void givenNoSqliteFile_WhenCreateIfNotExistsCalled_ThenDbInit() {
        logger.debug("Checking for db at: {}", db.filePath);

        db.createIfNotExists(sqlFile);
        logger.info("Ran createIfNotExists");

        logger.debug("Checking whether table exists");
        try(Connection conn = db.getConn()) {
            var dbMetaData = conn.getMetaData();
            ResultSet rs = dbMetaData.getTables(null, null, testTableName, new String[] {"TABLE"});
            assertTrue(rs.next());
        } catch (SQLException e) {
            logger.error("Failed in lookup of whether table exists");
            throw new RuntimeException(e);
        }
    }

    /**
     * Tests gym record inserting to db and then retrieval plus parsing
     */
    @Test
    void givenListGymRecords_WhenInsertAttempted_ThenFileNotFoundThrown() {
        db.initialiseDb(sqlFile);
        List<GymRecord> testInput = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            testInput.add(new GymRecord(
                    String.valueOf(i),
                    i,
                    i,
                    LocalDate.of(2025, i, 1),
                    LocalTime.of(14, i),
                    i
                    ,i
            ));
        }
        db.insertGymRecords(testInput, getGymRecordInsertSql(testTableName));
        var testOutput = db.extractAllGymRecords(testTableName);

        assertEquals(testInput, testOutput);
    }
}
