package DZ_3;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
public class Employee {
    private String name;
    private LocalDate hireDate; // Дата приема на работу
    private int salary; //зарплата
    private String position;

    public Employee(String name, String hireDate, int salary, String position) {
        this.name = name;
        this.hireDate = LocalDate.parse(hireDate); // формат "гггг-мм-дд"
        this.salary = salary;
        this.position=position;
    }

    @Override
    public String toString() {
        // String.format поможет сделать колонки ровными
        return String.format("Сотрудник: %-10s | Дата: %s | Зарплата: %-6d | Должность: %s",
                name, hireDate, salary, position);
    }



    // Метод для сравнения даты этого сотрудника с датой другого
    public void compareExperience(Employee other) {
        if (this.hireDate.isBefore(other.hireDate)) {
            System.out.println(this.name + " работает дольше, чем " + other.name);
        } else if (this.hireDate.isAfter(other.hireDate)) {
            System.out.println(this.name + " пришел позже, чем " + other.name);
        } else {
            System.out.println("Оба сотрудника пришли в один день");
        }
    }
}