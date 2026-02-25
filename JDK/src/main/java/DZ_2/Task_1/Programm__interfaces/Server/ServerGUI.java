package DZ_2.Task_1.Programm__interfaces.Server;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

@Getter
public class ServerGUI extends JFrame implements ServerView {
    private static final int POS_X = 500;
    private static final int POS_Y = 550;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 300;
    private static final String START = "Start";
    private static final String STOP = "Stop";
    private static final String STARTED = "Server started ";
    private static final String STOPPED = "Server stopped ";

    private final JTextArea log = new JTextArea();
    private final JScrollPane scrollLog = new JScrollPane(log);
    private final JButton btnStart = new JButton(START);
    private final JButton btnStop = new JButton(STOP);

    public Server server;

    public ServerGUI() {
        this.server = new Server(this);

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
        log.setLineWrap(true);       // Включает перенос строк
        log.setWrapStyleWord(true);  // Переносит по словам, а не по буквам
    }

    ////Слушатели кнопок
    private void setupListeners() {
        //Start
        btnStart.addActionListener(e -> {
            server.setServerWorking(true); // МЕНЯЕМ состояние в логике
            sendServerLogMessage(STARTED + "\n");
        });
        //Stop
        btnStop.addActionListener(e -> {
            server.setServerWorking(false); // МЕНЯЕМ состояние в логике
            sendServerLogMessage(STOPPED + "\n");
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

    @Override
    public void sendServerLogMessage(String message) {
        log.append(message);
    }
}