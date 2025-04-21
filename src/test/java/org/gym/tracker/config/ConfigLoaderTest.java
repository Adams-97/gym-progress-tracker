package org.gym.tracker.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ConfigLoaderTest {

    @Test
    void givenWrongEnvPath_WhenEnvRetrieved_ThenDotEnvException() {
        System.setProperty("ENV_LOCATION", System.getProperty("user.dir") + "/incorrect/path");
        assertThrows(DotenvException.class, ConfigLoader::getDotEnv);
    }

    @Test
    void givenCorrectEnvPath_WhenEnvRetrieved_ThenEnvValAccess() {
        System.setProperty("ENV_LOCATION", System.getProperty("user.dir") + "/src/test/resources/.env.test");
        Dotenv testConfig = ConfigLoader.getDotEnv();
        assertEquals(testConfig.get("TEST_KEY"), "1,2,3");
    }
}
