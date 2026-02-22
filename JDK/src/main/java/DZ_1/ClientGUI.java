package DZ_1;

import javax.swing.*;
import java.awt.*;

public class ClientGUI extends JFrame {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 300;

    private final ServerWindow serverWindow; // Ссылка на сервер

    private final JTextArea log = new JTextArea();
    private final JScrollPane scrollLog = new JScrollPane(log);

    private final JTextField tfIPAddress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8189");
    private final JTextField tfLogin = new JTextField();
    private final JPasswordField tfPassword = new JPasswordField("123456");
    private final JButton btnLogin = new JButton("Login");

    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");

    // Добавили JList (Список пользователей)
    private JList<String> userList;
    private final String[] users = {"ivan_igorevich", "masha", "petr"};

    ClientGUI(ServerWindow serverWindow) {
        this.serverWindow = serverWindow;

        //Конфигурируем окно Chat client - настраиваемые параметры
        settings();

        //Инициализируем верхнюю панель
        createPanelTop();

        //Инициализируем нижнюю панель
        createPanelBottom();

        //Настраиваем логику и связи
        setupListeners();

        setVisible(true);
        tfMessage.requestFocusInWindow(); //Чтобы курсор появлялся сразу в поле сообщения
    }

    //Настройки окна Chat client
    private void settings() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Завершает процесс при закрытии окна
        setLocationRelativeTo(null);
        setSize(WIDTH, HEIGHT);
        setTitle("Chat Client");
        log.setEditable(false);
    }

    //Слушатели кнопок и текстовых полей
    private void setupListeners() {
        //Для кнопки "Send"
        btnSend.addActionListener(e -> sendMessage());

        // Нажатия Enter для поля ввода текста сообщения клиента
        tfMessage.addActionListener(e -> {
            btnSend.doClick(); // Имитирует нажатие на кнопку Send
            tfMessage.requestFocusInWindow(); // Возвращает курсор обратно в поле после каждой отправки сообщения,
        });

        // Логика кнопки "login"
        btnLogin.addActionListener(e -> serverWindow.connectUser(this));
    }

    // Метод создания верхней панели для ввода данных клиента
    private void createPanelTop() {
        JPanel panelTop = new JPanel(new GridLayout(2, 3));
        panelTop.add(tfIPAddress);
        panelTop.add(tfPort);
        panelTop.add(tfLogin);
        panelTop.add(tfPassword);
        panelTop.add(btnLogin);
        add(panelTop, BorderLayout.NORTH);
    }

    // Метод создания нижней панели для отправки сообщений клиента и общих сообщений чата
    private void createPanelBottom() {
        JPanel panelBottom = new JPanel(new BorderLayout());
        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(btnSend, BorderLayout.EAST);
        add(panelBottom, BorderLayout.SOUTH);

        add(scrollLog, BorderLayout.CENTER);

        // --- ДОБАВЛЕНИЕ JList (Список пользователей) ---
        userList = new JList<>();
        userList.setListData(users); // заполняем список

        JScrollPane scrollUsers = new JScrollPane(userList);
        scrollUsers.setPreferredSize(new Dimension(100, 0)); // Ширина списка 100 пикселей
        add(scrollUsers, BorderLayout.EAST); // Размещаем справа
    }

    private void sendMessage() {
        String msg = tfMessage.getText();
        String login = tfLogin.getText(); // Имя из текстового поля

        // ПРОВЕРКА: если в JList выбрано имя, используем его вместо текстового поля
        if (userList.getSelectedValue() != null) {
            login = userList.getSelectedValue();
        }

        if (!msg.isEmpty()) {
            serverWindow.sendMessage(login + ": " + msg);
            tfMessage.setText("");
        }
    }

    // Метод, который вызовет сервер, чтобы передать клиенту сообщение
    public void receiveMessage(String message) {
        log.append(message + "\n");
    }

    public String getLogin() {
        // Если в списке что-то выбрано — берем это, иначе — берем из текстового поля
        if (userList.getSelectedValue() != null) {
            return userList.getSelectedValue();
        }
        return tfLogin.getText();
    }
}