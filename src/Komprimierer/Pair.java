package Komprimierer;

public class Pair<T extends Object, G extends Object> {
    private T key;
    private G value;

    public Pair(T key, G value) {
        this.key = key;
        this.value = value;
    }

    public T getKey() {
        return key;
    }

    public G getValue() {
        return value;
    }
}
