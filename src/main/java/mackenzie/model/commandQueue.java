package mackenzie.model;

import mackenzie.exceptions.*;

public class commandQueue {
    private final stack<String> inputStack = new stack<>(10);
    private final stack<String> outputStack = new stack<>(10);
    private final int maxSize = 10;

    public void enqueue(String command) throws invalidCommandException, stackException {
        if (!isCommandRecordable(command)) {
            throw new invalidCommandException("Comando inválido para gravação: " + command);
        }
        if (size() >= maxSize) {
            throw new invalidCommandException("Limite máximo de comandos gravados atingido.");
        }
        inputStack.push(command);
    }

    public String dequeue() throws stackException {
        if (outputStack.isEmpty()) {
            while (!inputStack.isEmpty()) {
                outputStack.push(inputStack.pop());
            }
        }
        return outputStack.isEmpty() ? null : outputStack.pop();
    }

    public boolean isEmpty() {
        return inputStack.isEmpty() && outputStack.isEmpty();
    }

    public int size() {
        return inputStackSize() + outputStackSize();
    }

    public void reset() {
        while (!inputStack.isEmpty()) {
            try {
                inputStack.pop();
            } catch (stackException ignored) {}
        }
        while (!outputStack.isEmpty()) {
            try {
                outputStack.pop();
            } catch (stackException ignored) {}
        }
    }

    public String get(int index) throws invalidCommandException, stackException {
        if (index < 0 || index >= size()) {
            throw new invalidCommandException("Índice fora do intervalo: " + index);
        }

        String[] all = new String[size()];
        int i = 0;

        stack<String> tempInput = new stack<>(10);
        stack<String> tempOutput = new stack<>(10);

        while (!outputStack.isEmpty()) {
            String value = outputStack.pop();
            all[i++] = value;
            tempOutput.push(value);
        }

        while (!tempOutput.isEmpty()) {
            outputStack.push(tempOutput.pop());
        }

        while (!inputStack.isEmpty()) {
            tempInput.push(inputStack.pop());
        }
        while (!tempInput.isEmpty()) {
            String value = tempInput.pop();
            all[i++] = value;
            inputStack.push(value);
        }

        return all[index];
    }

    private int inputStackSize() {
        int count = 0;
        stack<String> temp = new stack<>(10);
        while (!inputStack.isEmpty()) {
            try {
                temp.push(inputStack.pop());
                count++;
            } catch (stackException ignored) {}
        }
        while (!temp.isEmpty()) {
            try {
                inputStack.push(temp.pop());
            } catch (stackException ignored) {}
        }
        return count;
    }

    private int outputStackSize() {
        int count = 0;
        stack<String> temp = new stack<>(10);
        while (!outputStack.isEmpty()) {
            try {
                temp.push(outputStack.pop());
                count++;
            } catch (stackException ignored) {}
        }
        while (!temp.isEmpty()) {
            try {
                outputStack.push(temp.pop());
            } catch (stackException ignored) {}
        }
        return count;
    }

    public boolean isCommandRecordable(String command) {
        if (command == null) return false;
        String trimmed = command.trim().toUpperCase();
        if (trimmed.equals("REC") || trimmed.equals("STOP") || trimmed.equals("PLAY") || trimmed.equals("ERASE") || trimmed.equals("EXIT") || trimmed.equals("VARS") || trimmed.equals("RESET")) {
            return false;
        }
        if (command.matches("[a-zA-Z][a-zA-Z0-9]*\\s*=\\s*.+")) return true;
        if (command.matches("[\\dA-Za-z+\\-*/().\\s]+")) return true;
        return false;
    }
}