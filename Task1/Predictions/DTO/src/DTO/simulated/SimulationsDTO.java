package DTO.simulated;

import java.util.List;

public class SimulationsDTO {
    private final List<SimulationDetailsDTO> simulations;

    public SimulationsDTO(List<SimulationDetailsDTO> simulations) {
        this.simulations = simulations;
    }

    public List<SimulationDetailsDTO> getSimulations() {
        return simulations;
    }
}
