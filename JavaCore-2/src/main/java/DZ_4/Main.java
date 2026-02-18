package DZ_4;

import lombok.Getter;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    static void main(String[] args) {
        Employee employee_1 = new Employee("Алексей", Male.MALE, "директор");
        Employee employee_2 = new Employee("Эльвира", Male.FEMALE, "уборщица");
        Employee employee_3 = new Employee("Сергей", Male.MALE, "слесарь");
        Employee employee_4 = new Employee("Зинаида", Male.FEMALE, "бухгалтер");

        List<Employee> employeesList = List.of(new Employee[]{employee_1, employee_2, employee_3, employee_4});

        System.out.println("\n=== СПИСОК СОТРУДНИКОВ ФИРМЫ <РОГА И КОПЫТА>===");
        for (Employee emp : employeesList) {
            System.out.println(emp);
        }
        System.out.println("===========================================");

        Scanner scanner = new Scanner(System.in);

        // Определяем формат, в котором пользователь должен ввести дату
        // Например: день.месяц.год (15.05.2023)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        System.out.println("Введите дату в формате ДД.ММ.ГГГГ (например, 20.02.2024):");
        String userInput = scanner.nextLine();

        try {
            // Преобразуем (парсим) строку в LocalDate
            LocalDate localDate = LocalDate.parse(userInput, formatter);
            MonthDay currentDayMonth = MonthDay.from(localDate);
            congratulation(employeesList,currentDayMonth);
        } catch (DateTimeParseException e) {
            // Если пользователь ввел дату в неверном формате
            System.out.println("Ошибка! Неверный формат даты. Пожалуйста, используйте ДД.ММ.ГГГГ.");
        }
    }
    @Getter
    enum Holidays {
        NEW_YEAR("Новый год",12, 31),MARCH_8("8 Марта", 3, 8), FEBRUARY_23("23 Февраля",2,23);
        private final String description;
        private final MonthDay date;

        Holidays(String description, int month, int day) {
            this.description = description;
            this.date = MonthDay.of(month, day);
        }
    }

    public static void congratulation(List<Employee> employeeList, MonthDay currentDayMonth) {
        StringBuilder names = new StringBuilder();
        String holidayMessage = "";
        String prefix = "";

        // Определяем, какой сегодня праздник и кого будем поздравляем
        if (currentDayMonth.equals(Holidays.FEBRUARY_23.getDate())) {
            prefix = "Дорогие мужчины: ";
            holidayMessage = "Поздравляем вас с 23 Февраля";
            for (Employee emp : employeeList) {
                if (emp.getMale() == Male.MALE) { // Проверяем на мужчину
                    if (!names.isEmpty()) names.append(", "); // Ставим запятую, если это не первое имя
                    names.append(emp.getName());
                }
            }
        } else if (currentDayMonth.equals(Holidays.MARCH_8.getDate())) {
            prefix = "Дорогие женщины: ";
            holidayMessage = "Поздравляем вас с 8 Марта";
            for (Employee emp : employeeList) {
                if (emp.getMale() == Male.FEMALE) { // Проверяем на женщину
                    if (!names.isEmpty()) names.append(", ");
                    names.append(emp.getName());
                }
            }
        } else if (currentDayMonth.equals(Holidays.NEW_YEAR.getDate())) {
            prefix = "Уважаемые сотрудники: ";
            holidayMessage = "Поздравляем вас с Новым годом";
            for (Employee emp : employeeList) {
                if (!names.isEmpty()) names.append(", ");
                names.append(emp.getName());
            }
        }

        // Выводим результат, если кто-то был найден
        if (!names.isEmpty()) {
            System.out.println(prefix + names + "!");
            System.out.println(holidayMessage);
        } else {
            System.out.println("Сегодня обычный рабочий день.");
        }
    }
}