package com.adaptive.environments.pump_simulator.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "simulator.pump")
public class PumpSimulatorProperties {

    private boolean rpm;
    private boolean pressureIn;
    private boolean pressureOut;
    private boolean flowOut;
    private boolean fluidTempIn;
    private boolean fluidTempOut;
    private boolean motorTemp;
    private boolean inputPower;
}