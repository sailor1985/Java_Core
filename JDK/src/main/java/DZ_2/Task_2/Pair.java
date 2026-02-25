package DZ_2.Task_2;

import lombok.Getter;

/**
 * Обобщенный класс "Пара", хранящий два объекта одного типа.
 * @param <T> тип объектов в паре
 */

@Getter
public class Pair <T> {
    private final T first;
    private final T second;

    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
