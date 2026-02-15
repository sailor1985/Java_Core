package Sem_3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CatTest {

    @Test
    void add() {
        Cat cat = new Cat();
        Assertions.assertEquals(12,cat.add(5,7)
        );
    }
}