package org.project.Config;

import io.github.cdimascio.dotenv.Dotenv;
import java.util.Map;
import java.util.NoSuchElementException;

public class GymAppConfig {
    public final Map<String, String> configMap;
    private final Dotenv dotenvConf;
    private final Map<String, String> yamlConf;

    public GymAppConfig(Map<String, String> configMap, Dotenv dotEnvConf, Map<String, String> yamlConf) {
        this.configMap = configMap;
        this.dotenvConf = dotEnvConf;
        this.yamlConf = yamlConf;
    }

    /**
     * Try's all configs available before raising exception.
     * @return environment variable value or error from config value not existing.
     */
    private String getValue(String key) throws NoSuchElementException {
        for (AllConfigsEnum config: AllConfigsEnum.values()) {
            String maybeConfigValue;
            maybeConfigValue = tryGetConfigVal(config, key);

            if (maybeConfigValue != null) {
                return maybeConfigValue;
            }
        }
        throw new NoSuchElementException(String.format("No env var called: %s", key));
    }

    private String tryGetConfigVal(AllConfigsEnum config, String key) {
        String maybeConfigValue;
        switch (config) {
            case (AllConfigsEnum.SYSTEM):
                maybeConfigValue = System.getenv(key);
                break;
            case (AllConfigsEnum.DOTENV):
                maybeConfigValue = this.dotenvConf.get(key);
                break;
            case (AllConfigsEnum.YAMLS):
                maybeConfigValue = this.yamlConf.get(key);
                break;
            default:
                maybeConfigValue = null;
                break;
        }
        return maybeConfigValue;
    }
}

