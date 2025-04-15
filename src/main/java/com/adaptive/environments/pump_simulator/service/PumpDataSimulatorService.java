package com.adaptive.environments.pump_simulator.service;

import com.adaptive.environments.pump_simulator.config.PumpSimulatorProperties;
import com.adaptive.environments.pump_simulator.simulator.*;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("simulator")
public class PumpDataSimulatorService {

    private final PumpSimulatorProperties properties;
    private final List<Simulator> simulators;

    public PumpDataSimulatorService(PumpSimulatorProperties properties, List<Simulator> simulators) {
        this.properties = properties;
        this.simulators = simulators;
        startEnabledSimulations();
    }

    @PostConstruct
    private void startEnabledSimulations() {
        for (Simulator simulator : simulators) {
            if (isEnabled(simulator)) {
                simulator.simulate();
            }
        }
    }

    private boolean isEnabled(Simulator simulator) {
        return (simulator instanceof RpmSimulator && properties.isRpm()) ||
                (simulator instanceof PressureInSimulator && properties.isPressureIn()) ||
                (simulator instanceof PressureOutSimulator && properties.isPressureOut()) ||
                (simulator instanceof FlowOutSimulator && properties.isFlowOut()) ||
                (simulator instanceof FluidTempInSimulator && properties.isFluidTempIn()) ||
                (simulator instanceof FluidTempOutSimulator && properties.isFluidTempOut()) ||
                (simulator instanceof MotorTempSimulator && properties.isMotorTemp()) ||
                (simulator instanceof InputPowerSimulator && properties.isInputPower());
    }
}
