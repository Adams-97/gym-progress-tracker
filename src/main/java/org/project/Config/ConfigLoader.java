package org.project.Config;

import java.util.Objects;

public class ConfigLoader {
    final String ENV;

    /**
     * Initialises org.project.Config.ConfigLoader class based on system environment var.
     */
    ConfigLoader(String envString) {
        if (Objects.equals(envString, "PRD")) {this.ENV = "PRD";}
        else {this.ENV = "DEV";}

    }

    GymAppConfig getConfig() {
        return null;
    }
}
