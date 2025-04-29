package org.gym.tracker.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.gym.tracker.config.ConfigLoader.getDotEnv;
import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ConfigLoaderTest {

    @Nested
    class getDotEnv {
        @Test
        void givenInvalidEnvPath_WhenEnvRetrieved_ThenNoError() {
            String invalidEnvPath = "/incorrect/path";
            assertDoesNotThrow(() -> getDotEnv());
            assertDoesNotThrow(() -> getDotEnv(invalidEnvPath));
        }

        @Test
        void givenValidEnvPath_WhenEnvRetrieved_ThenEnvValAccess() {
            String validEnvPath = System.getProperty("user.dir") + "/src/test/resources/.env.test";
            Dotenv testConfig = getDotEnv(validEnvPath);
            assertEquals("1,2,3", testConfig.get("TEST_KEY"));
        }
    }




//    @Nested
//    class getYamlConfig {
//        @Test
//        void sandpit() {
//            Map<String, Object> input = new HashMap<>();
//
//        }
//    }
}
