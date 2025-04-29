package org.gym.tracker.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Optional;

/**
 * Gets environment dependent config key/values and returns to user
 */
public final class ConfigLoader {
    private static final Logger logger = LogManager.getLogger(ConfigLoader.class);
    private static Dotenv envVar;
    private static final String DEV_DOT_ENV_PATH = "/env/.env.dev";
    private static final String CONFIG_NAME = "/config/config.yml";
    private static YamlConfig yamlConfig;

    private ConfigLoader() {
         envVar = getDotEnv();
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
     * Loads a yaml file from resources directory.
     * Throws FileNotFoundException if CONFIG_NAME is incorrect
     */
    public static YamlConfig getYamlConfig() throws FileNotFoundException {
        InputStream inputStream = ConfigLoader.class.getResourceAsStream(CONFIG_NAME);
        if (inputStream == null) {
            throw new FileNotFoundException(String.format("Cannot find config file: %s", CONFIG_NAME));
        }

        logger.info("Yaml file {} loaded: ", CONFIG_NAME);
        return new Yaml(new Constructor(YamlConfig.class, new LoaderOptions())).load(inputStream);
    }

    /**
     * Gets ExcelConfig from config yaml
     */
    public static ExcelConfig getExcelConfig() {
        return yamlConfig.excelConfig();
    }

}
