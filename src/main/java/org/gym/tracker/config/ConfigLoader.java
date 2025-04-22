package org.gym.tracker.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;
import java.io.File;

/**
 * Gets environment dependent config key/values and returns to user
 */
public final class ConfigLoader {
    private ConfigLoader() {}

    /**
     * Loads dotenv config from environment dependent env file
     */
    public static Dotenv getDotEnv(String dotEnvPath) throws DotenvException {
        File envFile = new File(dotEnvPath);

        return Dotenv.configure()
            .directory(envFile.getParent())
            .filename(envFile.getName())
            .load();
    }

    public static Dotenv getDotEnv() throws DotenvException {
        String defaultDotEnvPath = System.getProperty("user.dir") + "/env/.env.dev";
        File envFile = new File(defaultDotEnvPath);

        return Dotenv.configure()
                .directory(envFile.getParent())
                .filename(envFile.getName())
                .load();
    }
}
