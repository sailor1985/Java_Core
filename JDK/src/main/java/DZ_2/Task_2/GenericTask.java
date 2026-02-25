package DZ_2.Task_2;

public class GenericTask {
    /**
     * Обобщенный метод для сложения чисел.
     * Используем ограничивающую маску (Wildcard) с Number,
     * чтобы метод работал с Integer, Double, Float и т.д.
     */
    public static double sum(Pair<? extends Number> pair) {
        return pair.getFirst().doubleValue() + pair.getSecond().doubleValue();
    }

    /**
     * Обобщенный метод для конкатенации строковых представлений любых объектов.
     */
    public static String concatenate(Pair<?> pair) {
        return pair.getFirst().toString() + " " + pair.getSecond().toString();
    }
}
