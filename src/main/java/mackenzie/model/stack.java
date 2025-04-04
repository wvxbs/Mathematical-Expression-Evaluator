package mackenzie.model;

import mackenzie.exceptions.*;

@SuppressWarnings("unchecked")
public class stack<T> {
    private final T[] data;
    private int top;

    public stack(int size) {
        data = (T[]) new Object[size];
        top = -1;
    }

    public void push(T value) throws stackException {
        if (top < data.length - 1) {
            data[++top] = value;
        } else {
            throw new stackException("Stack overflow");
        }
    }

    public T pop() throws stackException {
        if (top >= 0) {
            return data[top--];
        }
        throw new stackException("Stack underflow");
    }

    public T peek() throws stackException {
        if (top >= 0) {
            return data[top];
        }
        throw new stackException("Stack is empty");
    }

    public boolean isEmpty() {
        return top == -1;
    }
}