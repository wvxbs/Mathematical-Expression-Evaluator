package mackenzie.resources;

public class expressionEvaluator {
    private final variableManager variables;

    public expressionEvaluator(variableManager variables) {
        this.variables = variables;
    }

    public void evaluate(String infix) {
        try {
            infixToPostfixConverter converter = new infixToPostfixConverter(variables);
            String postfix = converter.convert(infix);
            postfixCalculator calculator = new postfixCalculator(variables);
            double result = calculator.evaluate(postfix);
            System.out.println("= " + result);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public double evaluateAndReturn(String infix) throws Exception {
        infixToPostfixConverter converter = new infixToPostfixConverter(variables);
        String postfix = converter.convert(infix);
        postfixCalculator calculator = new postfixCalculator(variables);
        return calculator.evaluate(postfix);
    }

    public void evaluateExpression(String infix) {
        evaluate(infix);
    }
}