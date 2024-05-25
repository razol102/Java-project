import DTO.*;
import DTO.simulated.SimulatedEntityDTO;
import DTO.simulated.SimulatedPropertyDTO;
import DTO.simulated.SimulationDetailsDTO;
import DTO.simulated.SimulationsDTO;
import simulation.impl.SimulationEngine;

import java.util.*;

public class FunctionUI {

    // ----------------------prints--------------------------


    public void printWorldDTO(WorldDTO worldDTO) {
        System.out.println("Simulation details:");
        printAllEntities(worldDTO.getEntites());
        printAllRules(worldDTO.getRules());
        printTermination(worldDTO.getTermination());
    }

    public void printAllEntities(List<EntityDTO> entities) {
        int i = 1;
        System.out.println("-------- Entities --------");
        for (EntityDTO entityDTO : entities) {
            System.out.println("Entity #" + i + ":");
            System.out.println("----------");
            System.out.println("Name: " + entityDTO.getName());
            System.out.println("Population: " + entityDTO.getPoplulation());
            System.out.println("Properties: \n");
            int j = 1;
            for (PropertyDTO property : entityDTO.getProperties()) {
                System.out.print("* Property #" + j + ": Name: " + property.getName() + ", ");
                System.out.print("type: " + property.getType() + ", ");
                if (property.getFrom() != null) {
                    if (property.getType().equalsIgnoreCase("decimal"))
                        System.out.print("Range: " + Math.round(property.getFrom()) + " - " + Math.round(property.getTo()) + ", ");
                    else    // Float
                        System.out.print("Range: " + property.getFrom() + " - " + property.getTo() + ", ");
                }
                System.out.println("Random initialized: " + property.getIsRandom());
                j++;
            }
            i++;
        }
        System.out.print("\n");
    }

    public void printAllRules(List<RuleDTO> rules) {
        int i = 1;
        System.out.println("\n-------- Rules --------");
        for (RuleDTO ruleDTO : rules) {
            System.out.println("Rule #" + i + ":");
            System.out.println("--------");
            System.out.print("Name: " + ruleDTO.getName() + ", ");
            System.out.println("Activation: Every " + ruleDTO.getTicks() + " ticks in probability of " + ruleDTO.getPropability());
            System.out.println("Number of actions: " + ruleDTO.getActionNames().size());
            System.out.print("Actions name: ");
            for (String actionName : ruleDTO.getActionNames())
                System.out.print(actionName + ", ");
            i++;
            System.out.println("\b\b\n");
        }
    }

    public void printTermination(TerminationDTO terminationDTO) {
        System.out.println("End conditions: ");
        System.out.println("---------------");
        int i = 1;
        if (terminationDTO.getTicks() != 0) {
            System.out.println("Condition #" + i + ": After " + terminationDTO.getTicks() + " ticks");
            i++;
        }
        if (terminationDTO.getSeconds() != 0) {
            System.out.println("Condition #" + i + ": After " + terminationDTO.getSeconds() + " seconds");
            i++;
        }
        System.out.print("\n");
    }

    // ----------------------prints--------------------------


    public void buildEnvironments(Scanner scanner, SimulationEngine simulationEngine) {
        Map<String, Object> environmentsValuesInput = new HashMap<>();

        // init to null all values
        for (EnvironmentDTO envDTO : simulationEngine.getEnvironmentsDTO()) {
            environmentsValuesInput.put(envDTO.getName(), null);
        }
        // gets values for environments for activation
        System.out.println("Please choose an environment that you'd like to initialize or 0 to continue to the simulation: ");
        int userChoice = -1;
        while (userChoice != 0) {
            try {
                int i = 0;
                System.out.println(i + " - Continue to simulation");
                for (EnvironmentDTO envDTO : simulationEngine.getEnvironmentsDTO()) {
                    System.out.println((i + 1) + " - " + envDTO.getName());
                    i++;
                }
                if(scanner.hasNextInt()) {
                    userChoice = scanner.nextInt();
                    if (userChoice < 0 || userChoice > i)
                        throw new InputMismatchException("Incorrect input! You must enter a number between 0 and " + i);
                    else if (userChoice != 0) {
                        EnvironmentDTO envDTO = simulationEngine.getEnvironmentsDTO().get(userChoice - 1);
                        System.out.println("You chose " + envDTO.getName());
                        System.out.println("Type: " + envDTO.getType());
                        System.out.println(envDTO.getRange());
                        Object newValue =
                        environmentsValuesInput.put(envDTO.getName(), getEnvValue(scanner, envDTO.getType(), envDTO.getFrom(), envDTO.getTo()));
                    }
                }
                else {
                    System.out.println("Incorrect input! You must enter a valid number.\n");
                    scanner.next(); // Consume the invalid input
                }

            } catch (InputMismatchException e) {
                System.out.println(e.getMessage() + "\n");
            }
        }

        simulationEngine.setActiveEnvironmentValues(environmentsValuesInput);
        Map<String, Object> activeEnvDTO = simulationEngine.getActivatedEnvDTO().getActiveEnvironments();
        System.out.println("\nActive environments for simulation: ");
        System.out.println("-----------------------------------");
        activeEnvDTO.forEach((key, value) -> {
            System.out.println("Name: " + key + ", value: " + value);
        });
        System.out.print("\n");
    }


    public Object getEnvValue(Scanner scanner, String type, Float from, Float to) {
        String environmentValueInsert;
        switch (type.toUpperCase()) {
            case "DECIMAL":
                while (true) {
                    try {
                        System.out.print("Please insert a decimal value for the environment: ");
                        environmentValueInsert = scanner.next();
                        Integer value =  Integer.parseInt(environmentValueInsert);
                        if(value < from || value > to) {
                            throw new IllegalArgumentException("The number you inserted is out of range. You must enter a number in range of " + from + " to " + to + "\n");
                        }
                        else
                            return value;

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input! You must enter an integer\n");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
            case "FLOAT":
                while (true) {
                    try {
                        System.out.print("Please insert a float value for the environment: ");
                        environmentValueInsert = scanner.next();
                        Float value =  Float.parseFloat(environmentValueInsert);
                        if(value < from || value > to) {
                            throw new IllegalArgumentException("The number you inserted is out of range. You must enter a number in range of " + from + " to " + to + "\n");
                        }
                        else
                            return value;

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input! You must enter a number\n");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
            case "BOOLEAN":
                while (true) {
                    try {
                        System.out.print("Please choose a boolean value for the environment: ");
                        System.out.print("1 - True");
                        System.out.print("2 - False");
                        environmentValueInsert = scanner.next();
                        int boolChoice =  Integer.parseInt(environmentValueInsert);
                        if (boolChoice == 1) {
                            return true;
                        } else if (boolChoice == 2)
                            return false;
                        else
                            throw new InputMismatchException();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input!");
                    }
                }
            case "STRING":
                System.out.print("Please insert a string value for the environment: ");
                environmentValueInsert = scanner.next();
                return environmentValueInsert;
        }
        return null;
    }

    public void showPreviousSimulation(Scanner scanner, SimulationsDTO simulationsDTO) {
        int numOfSimulations = simulationsDTO.getSimulations().size();
        if (numOfSimulations == 0)
            System.out.println("There are no simulations to show");
        else if (numOfSimulations == 1) {
            System.out.println("There's only one simulation occurred: ");
            System.out.println("Simulation #1: " + simulationsDTO.getSimulations().get(0).getTimeFormat() + "\n");
            printSimulation(scanner, simulationsDTO.getSimulations().get(0));
        } else {
            System.out.println("Please choose one of the following simulations: (1 - " + numOfSimulations + ")");
            while(true) {
                try {
                    int i = 1;
                    for (SimulationDetailsDTO simulation : simulationsDTO.getSimulations()) {
                        System.out.println("Simulation #" + i + ": " + simulation.getTimeFormat());
                        i++;
                    }
                    int userChoice = scanner.nextInt();
                    if(userChoice < 1 || userChoice > numOfSimulations) {
                        throw new InputMismatchException();
                    }
                    else {
                        printSimulation(scanner, simulationsDTO.getSimulations().get(userChoice - 1));
                        return;
                    }
                } catch (InputMismatchException e){
                    System.out.println("Invalid input! You must insert a number between: 1 and " + numOfSimulations);
                }
            }
        }
    }

    public void printSimulation(Scanner scanner, SimulationDetailsDTO simulationDetailsDTO) {
        if(simulationDetailsDTO.getEntities().get(0).getFinalPopulation() == 0) {
            System.out.println("All instances of the current entity have died in this simulation, so there is nothing related to show.");
        }
        else {
            System.out.println("Please choose which details you would like to see from this simulation: (1-2)");
            while(true) {
                try {
                    System.out.println("1 - entities amount");
                    System.out.println("2 - properties histogram");
                    int userChoice = scanner.nextInt();
                    if(userChoice !=1 && userChoice != 2) {
                        throw new InputMismatchException();
                    }
                    else {
                        if(userChoice == 1)        // option 1
                            printEntitiesAmount(simulationDetailsDTO.getEntities());
                        else                       // option 2
                            printHistogram(scanner, simulationDetailsDTO.getEntities());
                        return;
                    }
                }catch (InputMismatchException e){
                    System.out.println("Invalid input! You must insert a number: 1 or 2");
                }
            }
        }
    }

    // option 1
    public void printEntitiesAmount(List<SimulatedEntityDTO> simulatedEntityDTOList) {
        if(simulatedEntityDTOList.size() == 1) {
            System.out.println("There's only one type of entity in the current simulation: ");
            System.out.println("* " + simulatedEntityDTOList.get(0).getEntityName() + "- " + "initialized population: " +
                    simulatedEntityDTOList.get(0).getInitPopulation() + " , population after simulation: " + simulatedEntityDTOList.get(0).getFinalPopulation() + "\n");
        }
        else {
            System.out.println("Simulation's entities:");
            for(SimulatedEntityDTO simulatedEntityDTO: simulatedEntityDTOList) {
                System.out.println("* " + simulatedEntityDTO.getEntityName() + "- " + "initialized population: " +
                        simulatedEntityDTO.getInitPopulation() + " , population after simulation: " + simulatedEntityDTO.getFinalPopulation());
            }
        }
    }

    // option 2
    public void printHistogram(Scanner scanner, List<SimulatedEntityDTO> simulatedEntityDTOList) {
        if (simulatedEntityDTOList.size() == 1) {
            System.out.println("There's only one type of entity in the current simulation: " + simulatedEntityDTOList.get(0).getEntityName());
        printPropertiesBeforeHistogram(scanner, simulatedEntityDTOList.get(0).getSimulatedPropertyDTOList());
        }
        else {
            int numOfEntities = simulatedEntityDTOList.size();
            while (true) {
                try {
                    System.out.println("Please choose an entity: ");
                    int i = 1;
                    for (SimulatedEntityDTO simulatedEntityDTO : simulatedEntityDTOList) {
                        System.out.println(i + ". " + simulatedEntityDTO.getEntityName());
                        i++;
                    }
                    int userChoice = scanner.nextInt();
                    if (userChoice < 1 || userChoice > numOfEntities) {
                        throw new InputMismatchException();
                    } else {
                        List<SimulatedPropertyDTO> simulatedPropertyDTOList = simulatedEntityDTOList.get(userChoice - 1).getSimulatedPropertyDTOList();
                        printPropertiesBeforeHistogram(scanner, simulatedPropertyDTOList);
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! You must insert a number between: 1 and " + numOfEntities);
                }
            }
        }
    }


    public void printPropertiesBeforeHistogram(Scanner scanner, List<SimulatedPropertyDTO> simulatedPropertyDTOList)    {
        if(simulatedPropertyDTOList.size() == 1) {
            System.out.println("There's only one property for this entity in the current simulation:");
            System.out.println("---Property" + simulatedPropertyDTOList.get(0).getPropertyName() + "Histogram---");
            showHistogram(simulatedPropertyDTOList.get(0).getValues());
        }
        else {
            while (true) {
                try {
                    System.out.println("Choose the property to show it's values: ");
                    int j = 1;
                    for (SimulatedPropertyDTO simulatedPropertyDTO : simulatedPropertyDTOList) {
                        System.out.println(j + ". " + simulatedPropertyDTO.getPropertyName());
                        j++;
                    }
                    int userChoice2 = scanner.nextInt();
                    if (userChoice2 < 1 || userChoice2 > simulatedPropertyDTOList.size())
                        throw new InputMismatchException();
                    else {
                        System.out.println("---Property [" + simulatedPropertyDTOList.get(userChoice2 - 1).getPropertyName() + "] Histogram---");
                        showHistogram(simulatedPropertyDTOList.get(userChoice2 - 1).getValues());
                        return;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! You must insert a number between: 1 and " + simulatedPropertyDTOList.size());
                }
            }
        }
    }

    public void showHistogram(List<String> values) {

        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String str : values) {
            frequencyMap.put(str, frequencyMap.getOrDefault(str, 0) + 1);
        }

        int maxFrequency = frequencyMap.values().stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);

        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
            String label = entry.getKey();
            int frequency = entry.getValue();
            String bar = generateBar(frequency, maxFrequency);
            System.out.printf("%-10s %s (%d)%n", label, bar, frequency);
        }
    }

    public String generateBar(int frequency, int maxFrequency) {
        int barLength = (int) (40.0 * frequency / maxFrequency);
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < barLength; i++) {
            bar.append("#");
        }
        bar.append("]");
        return bar.toString();
    }
}



