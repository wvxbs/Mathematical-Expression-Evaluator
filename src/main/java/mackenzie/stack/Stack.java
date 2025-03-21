package mackenzie.stack;

public class Stack {
    private char[] elements;
    private int top;

    public Stack(int capacity) {
        elements = new char[capacity];
        top = -1;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == elements.length - 1;
    }

    public void push(char element) {
        if (!isFull()) {
            elements[++top] = element;
        }
    }

    public char pop() {
        return isEmpty() ? '\0' : elements[top--];
    }

    public char peek() {
        return isEmpty() ? '\0' : elements[top];
    }
}

