package org.gym.tracker.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.gym.tracker.config.ConfigLoader.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ConfigLoaderTest {

    private static final Logger logger = LogManager.getLogger(ConfigLoaderTest.class);

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
        void givenYamlConfig_WhenYamlParsed_ThenValuesRetrieved() {
            String validYamlConfPath = "/config.yml";
            URL yamlResource = ConfigLoaderTest.class.getResource(validYamlConfPath);

            assert yamlResource != null;
            Map<String, Object> yaml = getYamlConfig(yamlResource.getPath());
            assertEquals("test-value", yaml.get("testKey"));
            assertEquals("test-value2", yaml.get("nestedTest.nestedTest1"));

            var nestedTest3 = new ArrayList<>();
            nestedTest3.add(1);
            nestedTest3.add(2);
            nestedTest3.add(3);
            assertEquals(nestedTest3, yaml.get("nestedTest.nestedTest2.nestedValue"));
        }

        @Test
        void givenInvalidYamlConfig_WhenYamlParsed_ThenFileNotFound() {
            String invalidYamlConfPath = "/incorrect.yml";
            assertThrows(RuntimeException.class, () -> getYamlConfig(invalidYamlConfPath));
        }
    }
}
