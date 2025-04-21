package org.gym.tracker.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;

/**
 * Gets environment dependent config key/values and returns to user
 */
public final class ConfigLoader {
    private ConfigLoader() {}

    /**
     * Loads dotenv config from environment dependent env file
     */
    public static Dotenv getDotEnv() throws DotenvException {
        String envFilePath = Optional
                .ofNullable(System.getProperty("ENV_LOCATION"))
                .orElse(System.getProperty("user.dir") + "/env/.env.dev");

        File envFile = new File(envFilePath);

        return Dotenv.configure()
            .directory(envFile.getParent())
            .filename(envFile.getName())
            .load();
    }
}
