package pl.sda.training.management.app.utils;

public class Counter {
    private int count;

    public Counter() {
        count = 0;
    }

    public Counter(int init) {
        count = init;
    }

    public int get() {
        return count;
    }

    public void clear() {
        count = 0;
    }

    public void increment() {
        count++;
    }

    public void increment(int add) {
        count += add;
    }

    public String toString() {
        return "" + count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Counter counter = (Counter) o;

        return count == counter.count;
    }

    @Override
    public int hashCode() {
        return count;
    }
}
