package Sem_1_Graphical_interfaces;

import javax.swing.*;
import java.awt.*;

// Наследуемся от JFrame
public class GameWindow extends JFrame {
    private static final int WIDTH = 555;
    private static final int HEIGHT = 507;

    JButton btnStart, btnExit;
    SettingWindow settingWindow;
    Map map;

    GameWindow() {
        // Базовые настройки окна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setTitle("Крестики-Нолики");
        setResizable(false);

        // Создаем компоненты
        btnStart = new JButton("Новая игра");
        btnExit = new JButton("Выход");
        settingWindow = new SettingWindow(this);
        map = new Map();

        // Слушатели событий
        btnExit.addActionListener(e -> System.exit(0));
        btnStart.addActionListener(e -> settingWindow.setVisible(true));

        // Нижняя панель с кнопками
        JPanel panBottom = new JPanel(new GridLayout(1, 2));
        panBottom.add(btnStart);
        panBottom.add(btnExit);

        // Добавляем компоненты в окно
        add(panBottom, BorderLayout.SOUTH);
        add(map, BorderLayout.CENTER);

        setVisible(true);
    }

    // Метод, который вызывается из окна настроек
    void startNewGame(int mode, int sizeX, int sizeY, int winLen) {
        map.startNewGame(mode, sizeX, sizeY, winLen);
    }
}