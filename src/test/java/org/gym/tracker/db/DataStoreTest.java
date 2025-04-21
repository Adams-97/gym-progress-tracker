package org.gym.tracker.db;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class DataStoreTest {

    @ParameterizedTest(name = "Check Implementations of DataStore for row parsing")
    @MethodSource("DataStoreImplementations")
    void givenDataStoreInstance_WhenParseLineOfGymData_ThenIntSuccess(
            DataStore dataStore, String rowOfData, GymRecord expectedParsedRow
    ) {
        GymRecord parsedRow = dataStore.parseLineOfGymData(rowOfData);
        assertEquals(parsedRow, expectedParsedRow);
    }

    private static Collection<Arguments> DataStoreImplementations() {
        List<String> rowsToParse = new ArrayList<>();
        rowsToParse.add("Test1");

        return List.of(
                Arguments.of(new ExcelStore(), rowsToParse.get(0), null)
        );
    }
}
