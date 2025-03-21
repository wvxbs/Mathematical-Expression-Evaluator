package mackenzie.stack;

public class Stack {
    private char[] elements;
    private int top;

    public Stack(int capacity) throws IllegalArgumentException {
        if (capacity <= 0) {
            throw new IllegalArgumentException("A capacidade da pilha deve ser maior que zero.");
        }
        elements = new char[capacity];
        top = -1;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == elements.length - 1;
    }

    public void push(char element) throws IllegalStateException {
        if (isFull()) {
            throw new IllegalStateException("A pilha está cheia. Não é possível adicionar mais elementos.");
        }
        elements[++top] = element;
    }

    public char pop() throws IllegalStateException {
        if (isEmpty()) {
            throw new IllegalStateException("A pilha está vazia. Não é possível remover elementos.");
        }
        return elements[top--];
    }

    public char peek() throws IllegalStateException {
        if (isEmpty()) {
            throw new IllegalStateException("A pilha está vazia. Não há elementos para visualizar.");
        }
        return elements[top];
    }
}