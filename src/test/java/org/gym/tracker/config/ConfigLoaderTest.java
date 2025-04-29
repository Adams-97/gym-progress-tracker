package org.gym.tracker.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;

import static org.gym.tracker.config.ConfigLoader.*;
import static org.gym.tracker.config.YamlConfig.WORKBOOK_NAME;
import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ConfigLoaderTest {

    private static final Logger logger = LogManager.getLogger(ConfigLoader.class);

    @Nested
    class getDotEnv {
        @Test
        void givenInvalidEnvPath_WhenEnvRetrieved_ThenNoError() {
            String invalidEnvPath = "/incorrect/path";
            assertDoesNotThrow(() -> getDotEnv());
            assertDoesNotThrow(() -> getDotEnv(invalidEnvPath));
        }

        @Test
        void givenValidEnvPath_WhenEnvRetrieved_ThenEnvValAccess() throws URISyntaxException {
            String validTestEnvPath = "/.env.test";
            URL testEnvPath = ConfigLoaderTest.class.getResource(validTestEnvPath);

            if (testEnvPath == null) {
                logger.error("Could not find {}", validTestEnvPath);
                throw new RuntimeException("Can't find .env.test file for unit test");
            }
            Dotenv testConfig = getDotEnv(testEnvPath.toURI().getPath());
            assertEquals("1,2,3", testConfig.get("TEST_KEY"));
        }
    }

    @Nested
    class getYamlConfig {
        @Test
        void sandpit() throws FileNotFoundException {
            String validYamlConfPath = "/config.yml";
            URL testEnvPath = ConfigLoaderTest.class.getResource(validYamlConfPath);
            logger.info("Checking if valid path: {}", validYamlConfPath);
            assertNotNull(testEnvPath);

            YamlConfig yaml = getYamlConfig(validYamlConfPath);
            assertEquals("workout-log.xlsx", yaml.excelConfig.get(WORKBOOK_NAME));
        }
    }
}
