package api.config;

import api.config.ProjectConfig;
import org.aeonbits.owner.ConfigFactory;

public class ProjectSetUp {
    private ProjectSetUp() {

    }

    private static ProjectConfig config = ConfigFactory.create(ProjectConfig.class, System.getProperties());

    public static String getHost() {
        return config.host();
    }
}
