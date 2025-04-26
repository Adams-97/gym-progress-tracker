package org.gym.tracker.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * Gets environment dependent config key/values and returns to user
 */
public final class ConfigLoader {
    private static final Logger logger = LogManager.getLogger(ConfigLoader.class);
    private ConfigLoader() {}

    /**
     * Loads dotenv config from environment dependent env file.
     */
    public static Optional<Dotenv> getDotEnv(String dotEnvPath) {
        try {
            File envFile = new File(dotEnvPath);

            if (!envFile.exists()) {
                throw new FileNotFoundException(String.format("Can't find dotenv file: %s", dotEnvPath));
            }
            return Optional.of(Dotenv.configure()
                    .directory(envFile.getParent())
                    .filename(envFile.getName())
                    .load());

        } catch (DotenvException e) {
            logger.warn("Dotenv Exception caught:\n", e);
        } catch (FileNotFoundException e) {
            logger.warn(e.getMessage());
        }
        return Optional.empty();
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
}
