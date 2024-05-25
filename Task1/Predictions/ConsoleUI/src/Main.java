import simulation.impl.SimulationEngine;

import javax.xml.bind.JAXBException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome! let's start simulate");
        SimulationEngine simulationEngine = new SimulationEngine();
        boolean done = false;
        while (!done) {
            done = userChoiceHandle(getMenuChoice(scanner), scanner, simulationEngine);
        }
        scanner.close();
    }


    public static int getMenuChoice(Scanner scanner) {
        boolean validInput = false;
        int res = 0;
        while (!validInput) {
            printMenu();
                if(scanner.hasNextInt()) {
                    res = scanner.nextInt();
                    if (res >= 1 && res <= 5)
                        validInput = true;
                    else
                        System.out.println("Wrong input!");
                }
                else {
                    System.out.println("Incorrect input! You must enter a valid number.");
                    scanner.next(); // Consume the invalid input
                }
        }
        return res;
    }

    public static void printMenu() {
        System.out.println("-----------------------------------------");
        System.out.println("Please choose one of the following: (1-5)");
        System.out.println("1 - Load file");
        System.out.println("2 - Show simulation details");
        System.out.println("3 - Run simulation");
        System.out.println("4 - Show details from previous simulation");    // need to choose one of the previous simulations
        System.out.println("5 - Exit");
    }

    public static boolean userChoiceHandle(int userChoice, Scanner scanner, SimulationEngine simulationEngine) {
        boolean finish = false;
        FunctionUI functionUI = new FunctionUI();

        try {
            switch (userChoice) {
                case 1:   // read simulation file
                        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
                        System.out.print("Please enter File path: ");
                        String filePath = scanner.next();
                        System.out.print("Loading file");
                        for (int i = 0; i < 3; i++){
                            Thread.sleep(200);
                            System.out.print(".");
                        }
                        simulationEngine.loadSystemDetails(filePath);
                        System.out.println("File was loaded successfully!\n");

                    break;
                case 2:   // show simulation details
                    if (!simulationEngine.isLoaded())
                        throw new SimulationNotLoadedException("There is no file loaded to the system. You must load a file first.\n");
                    functionUI.printWorldDTO(simulationEngine.getWorldDTO());
                    break;
                case 3:   // activate simulation
                    if (!simulationEngine.isLoaded())
                        throw new SimulationNotLoadedException("There is no simulation to run. You must load a file first, so you can run a simulation.\n");
                    functionUI.buildEnvironments(scanner, simulationEngine);
                    simulationEngine.runSimulation();
                    break;
                case 4:   // show previous simulation details
                    if (!simulationEngine.isLoaded())
                        throw new SimulationNotLoadedException("There is no file loaded to the system. You must load a file first.\n");
                    functionUI.showPreviousSimulation(scanner, simulationEngine.getSimulationsDTO());
                    break;
                case 5:   // exit simulation
                    System.out.println("~ Exiting system, Goodbye! ~");
                    finish = true;
                    break;
            }
        } catch (SimulationNotLoadedException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (JAXBException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return finish;
    }



}