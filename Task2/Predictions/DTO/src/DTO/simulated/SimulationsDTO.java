package DTO.simulated;

import java.util.ArrayList;
import java.util.List;

public class SimulationsDTO {
    private final List<SimulationDetailsDTO> simulations;
    public SimulationsDTO() {
        simulations = new ArrayList<>();
    }
    public void addNewExecutionToList(SimulationDetailsDTO simulationDetailsDTO){
        simulations.add(simulationDetailsDTO);
    }
    public List<SimulationDetailsDTO> getSimulations() {
        return simulations;
    }
    public SimulationDetailsDTO getLastSimulatedDTO(){
        return simulations.get(simulations.size() - 1);
    }
}
