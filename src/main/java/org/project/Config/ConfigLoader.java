package org.project.Config;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.Map;
import java.util.Objects;

public class ConfigLoader {
    final String ENV;

    /**
     * Initialises org.project.Config.ConfigLoader class based on system environment var.
     */
    ConfigLoader(String envString) {
        if (Objects.equals(envString, "PRD")) {this.ENV = "prd";}
        else {this.ENV = "dev";}

    }

    GymAppConfig getConfig() {
        Dotenv dotenv = Dotenv.configure()
                .directory("/env")
                .filename(String.format(".env.%s", this.ENV))
                .load();


    }
}
