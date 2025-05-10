package org.gym.tracker.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Gets environment dependent config key/values and returns to user
 */
public final class ConfigLoader {
    private static final Logger logger = LogManager.getLogger(ConfigLoader.class);
    private static final String DEV_DOT_ENV_PATH = "/env/.env.dev";
    private static final String DEFAULT_CONFIG_PATH = "/config/config.yml";

    private ConfigLoader() {}

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
     * Loads and flattens a yaml file from file path.
     * Throws FileNotFoundException if yamlPath doesn't point to a valid file
     */
    public static Map<String, Object> getYamlConfig(String yamlPath) {
        logger.debug("Searching for {}", yamlPath);

        try(FileInputStream yamlFile = new FileInputStream(yamlPath)) {
            logger.info("Yaml file {} loaded: ", yamlPath);
            return flattenConf(new Yaml().load(yamlFile), ".", "");

        } catch (FileNotFoundException e) {
            logger.error("Can't find yaml {}", yamlPath);
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Parses and flattens any nesting in snakeYAML output. Example:
     * key1:
     *  key11: "val11"
     * becomes "key1.key11" -> "val11"
     */
    public static Map<String, Object> flattenConf(Map<?, ?> config, String separator, String prefix) {
        Map<String, Object> out = new HashMap<>();
        String adjustedSep;

        if (Objects.equals(prefix, "")) {
            adjustedSep = "";
        } else adjustedSep = separator;

        for (Map.Entry<?, ?> entry : config.entrySet()) {
            if (entry.getValue() instanceof Map<?, ?>) {
                out.putAll(flattenConf((Map<?, ?>) entry.getValue(), separator, prefix + adjustedSep + entry.getKey()));
            } else {
                out.put(prefix + adjustedSep + entry.getKey(), entry.getValue());
                }
            }
        return out;
    }

    /**
     * Loads a yaml file from path.
     * Throws FileNotFoundException if yamlPath doesn't point to a valid file
     */
    public static Map<String, Object> getYamlConfig() {return getYamlConfig(DEFAULT_CONFIG_PATH);}
}
