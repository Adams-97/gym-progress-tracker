package org.gym.tracker.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

/**
 * Gets environment dependent config key/values and returns to user
 */
public final class ConfigLoader {
    private static final Logger logger = LogManager.getLogger(ConfigLoader.class);
    private static Dotenv envVar;
    private static final String DEV_DOT_ENV_PATH = "/env/.env.dev";
    private static final String DEFAULT_CONFIG_PATH = "/config/config.yml";
    private static YamlConfig yamlConfig;

    private ConfigLoader() throws FileNotFoundException {
        envVar = getDotEnv();
        yamlConfig = getYamlConfig();
    }

    /**
     * Loads dotenv config. If dev it looks for .env.dev, if not it'll just get env var
     */
    public static Dotenv getDotEnv(String filePath) {
        File envFile = new File(filePath);

        return Dotenv.configure()
                .directory(envFile.getParent())
                .filename(envFile.getName())
                .ignoreIfMissing()
                .load();
    }

    /**
     * Loads dotenv config. If dev it looks for .env.dev, if not it'll just get env var
     */
    public static Dotenv getDotEnv() {return getDotEnv(DEV_DOT_ENV_PATH);}

    /**
     * Loads a yaml file from path.
     * Throws FileNotFoundException if yamlPath doesn't point to a valid file
     */
    public static YamlConfig getYamlConfig(String yamlPath) throws FileNotFoundException {
        logger.debug("Searching for {}", yamlPath);
        InputStream inputStream = ConfigLoader.class.getResourceAsStream(yamlPath);
        if (inputStream == null) {
            throw new FileNotFoundException(String.format("Cannot find config file: %s", yamlPath));
        }

        Constructor constructor = new Constructor(YamlConfig.class, new LoaderOptions());
        logger.info("Yaml file {} loaded: ", yamlPath);
        return new Yaml(constructor).load(inputStream);
    }

    /**
     * Loads a yaml file from CONFIG_PATH env var or default class location.
     * Throws FileNotFoundException if it can't find a file at either location
     */
    public static YamlConfig getYamlConfig() throws FileNotFoundException {
        logger.debug("Looking for yaml config in default locations");

        if (envVar.get("CONFIG_PATH") != null) {
            logger.info("CONFIG_PATH env var found, searching: {}", envVar.get("CONFIG_PATH"));
            return getYamlConfig(envVar.get("CONFIG_PATH"));

        } else if (new File(DEFAULT_CONFIG_PATH).exists()) {
            logger.info("yaml config found: {}", DEFAULT_CONFIG_PATH);
            return getYamlConfig(DEFAULT_CONFIG_PATH);

        } else throw new FileNotFoundException("No valid config file location set");
    }

    /**
     * Gets ExcelConfig from config yaml
     */
    public static Map<String, String> getExcelConfig() {return yamlConfig.excelConfig;}

}
