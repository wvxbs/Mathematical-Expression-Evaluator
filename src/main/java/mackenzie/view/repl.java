package mackenzie.view;

import mackenzie.exceptions.*;
import mackenzie.model.*;
import mackenzie.resources.*;

import java.util.Scanner;

public class repl {
    private variableManager variables = new variableManager();
    private commandQueue commandQueue = new commandQueue();
    private boolean isRecording = false;

    public void start() {
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println(">>> Mathematica Expression Evaluator! \nDigite EXIT para sair)");

        while (true) {
            System.out.print("> ");
            input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("EXIT")) break;

            try {
                if (input.equalsIgnoreCase("REC")) {
                    commandQueue.reset();
                    isRecording = true;
                    System.out.println("Iniciando gravação... (REC: 0/10)");
                    continue;
                }

                if (input.equalsIgnoreCase("STOP")) {
                    isRecording = false;
                    System.out.println("Encerrando gravação... (REC: " + commandQueue.size() + "/10)");
                    continue;
                }

                if (input.equalsIgnoreCase("ERASE")) {
                    commandQueue.reset();
                    System.out.println("Gravação apagada.");
                    continue;
                }

                if (input.equalsIgnoreCase("PLAY")) {
                    if (commandQueue.isEmpty()) {
                        System.out.println("Não há gravação para ser reproduzida.");
                    } else {
                        System.out.println("Reproduzindo gravação...");
                        for (int i = 0; i < commandQueue.size(); i++) {
                            String command = commandQueue.get(i);
                            System.out.println("> " + command);
                            handleCommand(command);
                        }
                    }
                    continue;
                }

                if (isRecording) {
                    commandQueue.enqueue(input);
                    System.out.println("(REC: " + commandQueue.size() + "/10) " + input);
                    continue;
                }

                handleCommand(input);

            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        System.out.println("Programa encerrado.");
    }

    private void handleCommand(String input) {
        try {
            if (input.equalsIgnoreCase("VARS")) {
                variables.printVariables();
            } else if (input.equalsIgnoreCase("RESET")) {
                variables.reset();
            } else if (input.matches("[a-zA-Z][a-zA-Z0-9]*\\s*=\\s*[-+a-zA-Z0-9*/().\\s]+")) {
                String[] parts = input.split("=", 2);
                String var = parts[0].trim();
                String expression = parts[1].trim();
                expressionEvaluator evaluator = new expressionEvaluator(variables);
                double value = evaluator.evaluateAndReturn(expression);
                variables.set(var, value);
                System.out.println(var + " = " + value);
            } else {
                expressionEvaluator evaluator = new expressionEvaluator(variables);
                evaluator.evaluate(input);
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}