package Sem_3;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cat {
    public static String voice;
    private  String name;
    private int age;
    public int add(int a, int b) {


        return a+b;
    }


}
