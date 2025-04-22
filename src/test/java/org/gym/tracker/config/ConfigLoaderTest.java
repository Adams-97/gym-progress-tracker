package org.gym.tracker.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;
import org.junit.jupiter.api.*;
import static org.gym.tracker.config.ConfigLoader.getDotEnv;
import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ConfigLoaderTest {

    @Test
    void givenInvalidEnvPath_WhenEnvRetrieved_ThenDotEnvException() {
        String invalidEnvPath = System.getProperty("user.dir") + "/incorrect/path";
        assertThrows(DotenvException.class, () -> getDotEnv(invalidEnvPath));
    }

    @Test
    void givenValidEnvPath_WhenEnvRetrieved_ThenEnvValAccess() {
        String validEnvPath = System.getProperty("user.dir") + "/src/test/resources/.env.test";
        Dotenv testConfig = getDotEnv(validEnvPath);
        assertEquals(testConfig.get("TEST_KEY"), "1,2,3");
    }

    @Test
    void givenNoArgument_WhenEnvRetrieved_ThenDefaultEnvReturned() {
        Dotenv defaultEnv = getDotEnv();
        assertEquals(defaultEnv.get("ENV"), "DEV");
    }
}
