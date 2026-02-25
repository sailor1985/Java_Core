package DZ_2.Task_1.Programm__interfaces.Client;

import DZ_2.Task_1.Programm__interfaces.Server.ServerGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientGUI extends JFrame implements ClientView {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 300;

    private final JTextArea log = new JTextArea();
    private final JScrollPane scrollLog = new JScrollPane(log);

    private final JTextField tfIPAddress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8189");
    private final JTextField tfLogin = new JTextField();
    private final JPasswordField tfPassword = new JPasswordField("123456");
    private final JButton btnLogin = new JButton("Login");

    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");
    private JPanel panelTop;
    private JPanel panelBottom;

    private Client client;

    // Добавили JList (Список пользователей)
    private JList<String> userList;
    String login;

    public ClientGUI(ServerGUI server) {
        this.client = new Client(this, server.getServer());
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
        btnSend.addActionListener(e -> {
            sendMessageToServer();
        });

        // Нажатия Enter для поля ввода текста сообщения клиента
        tfMessage.addActionListener(e -> {
            btnSend.doClick(); // Имитирует нажатие на кнопку Send
            tfMessage.requestFocusInWindow(); // Возвращает курсор обратно в поле после каждой отправки сообщения,
        });

        // Для списка пользователей (JList) - когда кликаем на имя в списке, оно само копируется в текстовое поле
        userList.addListSelectionListener(e -> {
            // valueIsAdjusting становится true, когда пользователь только нажал,
            // и false, когда отпустил кнопку мыши. Нам нужно событие "отпустил".
            if (!e.getValueIsAdjusting()) {
                String selected = userList.getSelectedValue();
                if (selected != null) {
                    tfLogin.setText(selected);
                }
            }
        });

        // Для кнопки "Login" (берет текст из поля, зная, что он там актуален)
        btnLogin.addActionListener(e -> {
            login = tfLogin.getText();

            if (login.isEmpty()) {
                showMessage ("Ошибка: Имя пользователя не может быть пустым!");
            } else {
                client.connectToServer(login);
            }
        });

        // Для кнопки "Login"(опционально) - нажатие Enter в поле логина
        tfLogin.addActionListener(e -> {
            btnLogin.doClick();// Просто имитируем нажатие на кнопку Login
        });

        //При закрытии окна, чтобы сервер не держал «мертвое» соединение.
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                client.disconnect();
            }
        });
        }

     // Метод создания верхней панели для ввода данных клиента
    private void createPanelTop() {
        panelTop = new JPanel(new GridLayout(2, 3));
        panelTop.add(tfIPAddress);
        panelTop.add(tfPort);
        panelTop.add(tfLogin);
        panelTop.add(tfPassword);
        panelTop.add(btnLogin);
        add(panelTop, BorderLayout.NORTH);

        // --- ДОБАВЛЕНИЕ JList (Список пользователей) ---
        userList = new JList<>(client.getUsers());

        JScrollPane scrollUsers = new JScrollPane(userList);
        scrollUsers.setPreferredSize(new Dimension(100, 0)); // Ширина списка 100 пикселей
        add(scrollUsers, BorderLayout.EAST); // Размещаем справа
    }

    // Метод создания нижней панели для отправки сообщений клиента и общих сообщений чата
    private void createPanelBottom() {
        panelBottom = new JPanel(new BorderLayout());
        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(btnSend, BorderLayout.EAST);
        add(panelBottom, BorderLayout.SOUTH);
        add(scrollLog, BorderLayout.CENTER);
        panelBottom.setVisible(false);
    }

    // Метод, который вызовет сервер, чтобы передать клиенту в лог сообщение
    @Override
    public void sendClientLogMessage(String message) {
        log.append(message + "\n");
    }

    @Override
    // Показываем авторизацию, скрываем ввод сообщений
    public void disconnectFromServer() {
        panelTop.setVisible(true);
        panelBottom.setVisible(false);
    }

    @Override
    // Скрываем авторизацию, показываем чат
    public void connectedToServer() {
            panelTop.setVisible(false);      // Скрываем верхнюю панель (логин/пароль)
            panelBottom.setVisible(true);   // Показываем нижнюю панель (поле ввода и кнопка Send)
            scrollLog.setVisible(true);
    }

    @Override
    public void sendMessageToServer() {
        client.sendMessageToServer(tfMessage.getText());
        tfMessage.setText("");
    }

    public void showMessage (String text) {
        sendClientLogMessage(text);
    }
}