package DZ_1;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ServerWindow extends JFrame {
    private static final int POS_X = 500;
    private static final int POS_Y = 550;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 300;
    private static final String START = "Start";
    private static final String STOP = "Stop";
    private static final String LOG_PATH = "src/main/resources/chat_history.txt"; // Путь к файлу истории
    private boolean isServerWorking;

    private final JTextArea log = new JTextArea();
    private final JScrollPane scrollLog = new JScrollPane(log);
    private final JButton btnStart = new JButton(START);
    private final JButton btnStop = new JButton(STOP);

    private final List<ClientGUI> clients = new ArrayList<>(); // Список подключенных клиентов

    public ServerWindow() {
        isServerWorking = false;

        //Конфигурация окна Chat server - настраиваемые параметры
        settings();

        //Инициализируем панель с кнопками
        createPanel();

        //Настраиваем логику и связи
        setupListeners();

        setVisible(true);
    }

    //Настройки окна Chat server
    private void settings() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Chat server");
        setAlwaysOnTop(true);
        log.setEditable(false);
    }

    ////Слушатели кнопок
    private void setupListeners() {
        //Start
        btnStart.addActionListener(e -> {
            isServerWorking = true;
            log.append("Server started " + isServerWorking + "\n");
        });
        //Stop
        btnStop.addActionListener(e -> {
            isServerWorking = false;
            log.append("Server stopped " + isServerWorking + "\n");
        });
    }

    // Метод создания панели с кнопками Start и Stop
    private void createPanel() {
        JPanel panBottom = new JPanel(new GridLayout(1, 2));
        panBottom.add(btnStart);
        panBottom.add(btnStop);
        add(panBottom, BorderLayout.SOUTH);
        add(scrollLog, BorderLayout.CENTER);
    }

    public void sendMessage(String message) {
        if (!isServerWorking) return;
        log.append(message + "\n");
        saveLogToFile(message); // СОХРАНЯЕМ в файл каждое сообщение
        broadcastMessage(message);
    }

    private void broadcastMessage(String message) {
        for (ClientGUI client : clients) {
            client.receiveMessage(message);
        }
    }

    public void connectUser(ClientGUI client) {
        if (isServerWorking) {
            clients.add(client);
            log.append(client.getLogin() + " подключился к беседе\n");

            // ПРИ ПОДКЛЮЧЕНИИ: отправляем клиенту всю историю из файла
            String history = readLogFromFile();
            client.receiveMessage(history);
        }
    }

    // Запись строки в файл (true - значит дописывать в конец)
    private void saveLogToFile(String text) {
        try (FileWriter writer = new FileWriter(LOG_PATH, true)) {
            writer.write(text + "\n");
        } catch (IOException e) {
            log.append("Ошибка записи в файл: " + e.getMessage() + "\n");
        }
    }

    // Чтение всего файла целиком
    private String readLogFromFile() {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (FileNotFoundException e) {
            return ""; // Если файла нет, просто возвращаем пустую строку
        } catch (IOException e) {
            log.append("Ошибка чтения файла: " + e.getMessage() + "\n");
        }
        return sb.toString();
    }
}