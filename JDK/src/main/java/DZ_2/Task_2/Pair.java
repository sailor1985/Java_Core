package DZ_2.Task_2;

/**
 * Обобщенный класс "Пара", хранящий два объекта одного типа.
 * @param <T> тип объектов в паре
 */

public class Pair {
    private final T first;
    private final T second;

    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
