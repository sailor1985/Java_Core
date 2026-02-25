package DZ_2.Task_2;

public class Main {
    static void main(String[] args) {
        // 1. Работа с числами (Integer)
        Pair<Integer> intPair = new Pair<>(10, 20);
        System.out.println("Пара чисел: " + intPair);
        System.out.println("Сумма: " + sum(intPair));

        System.out.println("---");

        // 2. Работа с числами с плавающей точкой (Double)
        Pair<Double> doublePair = new Pair<>(5.5, 4.5);
        System.out.println("Пара double: " + doublePair);
        System.out.println("Сумма: " + sum(doublePair));

        System.out.println("---");

        // 3. Работа со строками
        Pair<String> stringPair = new Pair<>("Hello", "Java!");
        System.out.println("Пара строк: " + stringPair);
        System.out.println("Конкатенация: " + concatenate(stringPair));

        // Попытка вызвать sum для строк вызовет ошибку компиляции, что обеспечивает безопасность типов
        // sum(stringPair); // Ошибка: String не наследует Number
    }
}
