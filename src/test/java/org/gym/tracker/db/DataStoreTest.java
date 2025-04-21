package org.gym.tracker.db;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataStoreTest {
    @ParameterizedTest
    @MethodSource("DataStoreImplementations")
    void givenDataStoreInstance_WhenParseLineOfGymData_ThenIntSuccess(
            DataStore dataStore, List<String> rowOfData, GymRecord expectedParsedRow
    ) {
        GymRecord parsedRow = dataStore.parseLineOfGymData(rowOfData);
        assertEquals(parsedRow, expectedParsedRow);
    }

    private static Collection<Arguments> DataStoreImplementations() {
        return List.of(
                Arguments.of(new ExcelStore(), "test", null)
        );
    }
    }
}
