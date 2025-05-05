package org.gym.tracker.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class GymAppConfig {

    private static final Logger logger = LogManager.getLogger(GymAppConfig.class);
    private final Dotenv envVar;
    private final Map<String, Object> yaml;

    /**
     * Gets env var via dotenv if available and then searches for yaml
     */
    public GymAppConfig() throws IOException {
        if (!Boolean.parseBoolean(System.getenv("DEFAULT_ENV"))) {
            envVar = ConfigLoader.getDotEnv(System.getenv("DOTENV_PATH"));
        } else envVar = ConfigLoader.getDotEnv();

        if (!Boolean.parseBoolean(envVar.get("DEFAULT_CONF"))) {
            yaml = ConfigLoader.getYamlConfig(envVar.get("CONFIG_LOCATION"));
        } else yaml = ConfigLoader.getYamlConfig();
    }

    /**
     * Returns config values in order of env var > java props > yaml config.
     * Default behaviour is to error if value not found
     */
    private Optional<Object> searchConfigs(String key) {
        return Stream.of(envVar.get(key), System.getProperty(key), yaml.get(key))
                .filter(Objects::nonNull)
                .findFirst();
    }

    /**
     * Searches config for key but throws error if value cannot be found
     */
    public Object getValue(String key) {
        Optional<Object> searchResult = searchConfigs(key);
        try {
            return searchResult.orElseThrow();
        }
        catch (NoSuchElementException e) {
            logger.error("Cannot find key {} in config", key);
            throw e;
        }
    }

    /**
     * Searches config for key but does not throw error if not found
     */
    public Optional<Object> safeGetValue(String key) {return searchConfigs(key);}
}
