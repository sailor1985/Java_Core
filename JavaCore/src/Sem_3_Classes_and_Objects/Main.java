package Sem_3_Classes_and_Objects;

import java.lang.reflect.Method;
import java.util.Random;

public class Main {
    static void main() {
        var a = Math.class;
        Random random = new Random();
        Class <?> b = random.getClass();
        for (Method declaredMethod : b.getDeclaredMethods()) {
            System.out.println(declaredMethod);
        }

    }
}
