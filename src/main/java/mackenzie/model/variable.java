package mackenzie.model;

public class variable {
    private final String name;
    private final double value;

    public variable(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }
}
