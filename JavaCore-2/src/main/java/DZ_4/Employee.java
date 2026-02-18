package DZ_4;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {
    private String name;
    private Male male;
    private String position;

    @Override
    public String toString() {
        return String.format("Сотрудник: %-12s | Пол: %-8s | Должность: %-15s", name, male.getGenderName(), position);
    }
}