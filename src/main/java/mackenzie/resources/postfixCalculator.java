package mackenzie.resources;

import mackenzie.exceptions.*;
import mackenzie.model.*;

public class postfixCalculator {
    private final variableManager variables;

    public postfixCalculator(variableManager vars) {
        this.variables = vars;
    }

    public double evaluate(String postfix) throws evaluationException {
        stack<Double> stk = new stack<>(200);
        String[] tokens = postfix.trim().split("\\s+");

        try {
            for (String token : tokens) {
                if (token.matches("[-+]?\\d+(\\.\\d+)?")) {
                    stk.push(Double.parseDouble(token));
                } else if (token.matches("[a-zA-Z][a-zA-Z0-9]*")) {
                    stk.push(variables.get(token));
                } else if (token.matches("[+\\-*/]")) {
                    if (stk.isEmpty()) throw new evaluationException("Operador '" + token + "' sem operandos suficientes (1).");
                    double b = stk.pop();
                    if (stk.isEmpty()) throw new evaluationException("Operador '" + token + "' sem operandos suficientes (2).");
                    double a = stk.pop();
                    switch (token) {
                        case "+": stk.push(a + b); break;
                        case "-": stk.push(a - b); break;
                        case "*": stk.push(a * b); break;
                        case "/":
                            if (b == 0) throw new evaluationException("Divisão por zero");
                            stk.push(a / b);
                            break;
                        default: throw new evaluationException("Operador inválido: " + token);
                    }
                } else {
                    throw new evaluationException("Token inválido: " + token);
                }
            }

            if (stk.isEmpty()) {
                throw new evaluationException("Expressão vazia ou incompleta.");
            }

            double result = stk.pop();

            if (!stk.isEmpty()) {
                throw new evaluationException("Expressão malformada: sobram elementos na pilha.");
            }

            return result;
        } catch (stackException e) {
            throw new evaluationException("Erro de pilha: " + e.getMessage());
        }
    }
}