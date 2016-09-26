package net.anotheria.strel.echo;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

/**
 * @author Strel97
 */
@ConfigureMe(name = "distributeme")
public class DistributemeConfig {
    @Configure
    private String registryContainerHost;

    @Configure
    private int registryContainerPort;

    private static final DistributemeConfig instance = new DistributemeConfig();

    private DistributemeConfig() {
    }

    public static DistributemeConfig getInstance() {
        return instance;
    }

    public String getRegistryContainerHost() {
        return registryContainerHost;
    }

    public void setRegistryContainerHost(String host) {
        this.registryContainerHost = host;
    }

    public int getRegistryContainerPort() {
        return registryContainerPort;
    }

    public void setRegistryContainerPort(int registryContainerPort) {
        this.registryContainerPort = registryContainerPort;
    }
}
