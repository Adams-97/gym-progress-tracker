package org.project.Config;

import java.util.Map;
import java.util.NoSuchElementException;

public class GymAppConfig {
    final Map<String, String> configMap;

    public GymAppConfig(Map<String, String> configMap) {
        this.configMap = configMap;
    }

    /**
     * Try's all configs available before raising exception.
     * @return environment variable value or error from config value not existing.
     */
    private String getValue(String k) throws NoSuchElementException {
        String maybeValue = this.configMap.get(k);
        if (maybeValue == null && System.getenv(k) == null) {
            throw new NoSuchElementException(String.format("No env var called: %s", k));
        } else {
            return maybeValue;
        }
    }
}
