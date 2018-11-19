package pl.edu.agh.to2.yadc.config;

public class GlobalConfig {

    private static Configuration configuration;

    public static void setGlobalConfig(Configuration config) {
        configuration = config;
    }

    public static Configuration getGlobalConfig() {
        return configuration;
    }

}