package DZ_2.Task_1.Programm__interfaces.Repository;

import DZ_2.Task_1.Programm__interfaces.Server.ServerView;

import java.io.*;

public class Repository {

    private final ServerView view;
    private static final String ERROR_WRITE = "Ошибка записи в файл: ";
    private static final String ERROR_READ = "Ошибка чтения в файл: ";

    private static final String LOG_PATH = "src/main/resources/chat_history.txt"; // Путь к файлу истории

    public Repository(ServerView view) {
        this.view = view;
    }

    // Запись строки в файл (true - значит дописывать в конец)
    public void saveLogToFile(String text) {
        try (FileWriter writer = new FileWriter(LOG_PATH, true)) {
            writer.write(text + "\n");
        } catch (IOException e) {
            if (view != null) {
                view.sendServerLogMessage(ERROR_WRITE + e.getMessage());
            }
        }
    }

    // Чтение всего файла целиком
    public String readLogFromFile() {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (FileNotFoundException e) {
            return ""; // Если файла нет, просто возвращаем пустую строку
        } catch (IOException e) {
            if (view != null) {
                view.sendServerLogMessage(ERROR_READ + e.getMessage());
            }
        }
        return sb.toString();
    }
}
