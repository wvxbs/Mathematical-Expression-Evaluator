package mackenzie.resources;

import mackenzie.model.*;
import mackenzie.exceptions.stackException;

public class infixToPostfixConverter {
    private final variableManager variables;

    public infixToPostfixConverter(variableManager variables) {
        this.variables = variables;
    }

    public String convert(String infix) {
        StringBuilder postfix = new StringBuilder();
        stack<Character> stk = new stack<>(1000);
        char[] tokens = infix.replaceAll("\\s+", "").toCharArray();

        try {
            for (int i = 0; i < tokens.length; i++) {
                char token = tokens[i];

                if (Character.isLetterOrDigit(token) || token == '.') {
                    postfix.append(token);
                    if (i == tokens.length - 1 || (!Character.isLetterOrDigit(tokens[i + 1]) && tokens[i + 1] != '.')) {
                        postfix.append(' ');
                    }
                } else if (token == '(') {
                    stk.push(token);
                } else if (token == ')') {
                    while (!stk.isEmpty() && stk.peek() != '(') {
                        postfix.append(stk.pop()).append(' ');
                    }
                    if (!stk.isEmpty() && stk.peek() == '(') {
                        stk.pop();
                    } else {
                        throw new RuntimeException("Parêntese fechado sem correspondente aberto.");
                    }
                } else if (isOperator(token)) {
                    while (!stk.isEmpty() && stk.peek() != '(' && precedence(stk.peek()) >= precedence(token)) {
                        postfix.append(stk.pop()).append(' ');
                    }
                    stk.push(token);
                } else {
                    throw new RuntimeException("Caractere inválido na expressão: " + token);
                }
            }

            while (!stk.isEmpty()) {
                char op = stk.pop();
                if (op == '(' || op == ')') {
                    throw new RuntimeException("Parênteses desbalanceados.");
                }
                postfix.append(op).append(' ');
            }
        } catch (stackException e) {
            throw new RuntimeException("Erro na pilha: " + e.getMessage());
        }

        return postfix.toString().trim();
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private int precedence(char op) {
        return switch (op) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            default -> 0;
        };
    }
}