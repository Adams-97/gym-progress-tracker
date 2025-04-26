package org.gym.tracker.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.gym.tracker.config.ConfigLoader.getDotEnv;
import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ConfigLoaderTest {

    @Test
    void givenInvalidEnvPath_WhenEnvRetrieved_ThenDotEnvException() {
        String invalidEnvPath = "/incorrect/path";
        assertEquals(getDotEnv(invalidEnvPath), Optional.empty());
    }

    @Test
    void givenValidEnvPath_WhenEnvRetrieved_ThenEnvValAccess() {
        String validEnvPath = System.getProperty("user.dir") + "/src/test/resources/.env.test";
        Optional<Dotenv> testConfig = getDotEnv(validEnvPath);
        assertTrue(testConfig.isPresent());
    }
}
