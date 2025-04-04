package mackenzie.resources;

import mackenzie.model.variable;
import mackenzie.model.stack;
import mackenzie.exceptions.stackException;

public class variableManager {
    private stack<variable> variables;

    public variableManager() {
        this.variables = new stack<>(2);
    }

    public void set(String name, double value) {
        try {
            if (isFull()) {
                expandStack();
            }

            stack<variable> temp = new stack<>(variablesSize());
            boolean updated = false;

            while (!variables.isEmpty()) {
                variable var = variables.pop();
                if (var.getName().equals(name)) {
                    updated = true;
                    break;
                }
                temp.push(var);
            }

            while (!temp.isEmpty()) {
                variables.push(temp.pop());
            }

            variables.push(new variable(name, value));
        } catch (stackException e) {
            throw new RuntimeException("Erro ao definir variável: " + e.getMessage());
        }
    }

    public double get(String name) {
        try {
            stack<variable> temp = new stack<>(variablesSize());

            while (!variables.isEmpty()) {
                variable var = variables.pop();
                temp.push(var);
                if (var.getName().equals(name)) {
                    restoreStack(temp);
                    return var.getValue();
                }
            }

            restoreStack(temp);
            throw new IllegalArgumentException("Variável não definida: " + name);
        } catch (stackException e) {
            throw new RuntimeException("Erro ao acessar variável: " + e.getMessage());
        }
    }

    public boolean contains(String name) {
        try {
            stack<variable> temp = new stack<>(variablesSize());
            boolean found = false;

            while (!variables.isEmpty()) {
                variable var = variables.pop();
                if (var.getName().equals(name)) {
                    found = true;
                }
                temp.push(var);
            }

            restoreStack(temp);
            return found;
        } catch (stackException e) {
            return false;
        }
    }

    public void printVariables() {
        try {
            if (variables.isEmpty()) {
                System.out.println("Nenhuma variável definida.");
                return;
            }

            stack<variable> temp = new stack<>(variablesSize());

            System.out.println("Variáveis definidas:");
            while (!variables.isEmpty()) {
                variable var = variables.pop();
                System.out.println(var.getName() + " = " + var.getValue());
                temp.push(var);
            }

            restoreStack(temp);
        } catch (stackException e) {
            System.out.println("Erro ao imprimir variáveis: " + e.getMessage());
        }
    }

    public void reset() {
        while (!variables.isEmpty()) {
            try {
                variables.pop();
            } catch (stackException e) {
                break;
            }
        }
    }

    private void restoreStack(stack<variable> temp) throws stackException {
        while (!temp.isEmpty()) {
            variables.push(temp.pop());
        }
    }

    private boolean isFull() {
        try {
            stack<variable> test = new stack<>(variablesSize());
            test.push(new variable("test", 0));
            return false;
        } catch (stackException e) {
            return true;
        }
    }

    private void expandStack() throws stackException {
        stack<variable> newStack = new stack<>(variablesSize() * 2);
        stack<variable> temp = new stack<>(variablesSize());

        while (!variables.isEmpty()) {
            temp.push(variables.pop());
        }

        while (!temp.isEmpty()) {
            newStack.push(temp.pop());
        }

        this.variables = newStack;
    }

    private int variablesSize() {
        return 100;
    }
}