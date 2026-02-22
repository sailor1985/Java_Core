package Sem_1_Graphical_interfaces;

import javax.swing.*;
import java.awt.*;

public class SettingWindow extends JFrame {
    private static final int WIDTH = 230;
    private static final int HEIGHT = 350;

    // Константы для текста (исправлена кодировка)
    private final String currentWinValue = "Длина для победы: ";
    private final String currentFieldValue = "Размер поля: ";

    // Элементы интерфейса
    JRadioButton humanVsAi;
    JRadioButton humanVsHuman;
    ButtonGroup buttonGroup;
    JButton btnStart;
    JSlider sliderFieldSize;
    JSlider sliderWinSize;
    JLabel currentFieldSize;
    JLabel currentWinSize;

    SettingWindow(GameWindow gameWindow) {
        // Настройка параметров окна
        setTitle("Настройки");
        setSize(WIDTH, HEIGHT);
        // Центрируем относительно главного окна
        setLocationRelativeTo(gameWindow);

        // Панель с сеточкой (9 строк, 1 колонка)
        setLayout(new GridLayout(10, 1));

        // 1. Выбор режима игры
        add(new JLabel("Выберите режим игры:"));
        humanVsAi = new JRadioButton("Человек VS ИИ", true);
        humanVsHuman = new JRadioButton("Человек VS Человек");
        buttonGroup = new ButtonGroup();
        buttonGroup.add(humanVsAi);
        buttonGroup.add(humanVsHuman);
        add(humanVsAi);
        add(humanVsHuman);

        // 2. Настройка размера поля
        add(new JLabel("Выберите размер поля:"));
        currentFieldSize = new JLabel(currentFieldValue + "3");
        add(currentFieldSize);
        sliderFieldSize = new JSlider(3, 10, 3);
        add(sliderFieldSize);

        // 3. Настройка длины победной линии
        add(new JLabel("Выберите длину для победы:"));
        currentWinSize = new JLabel(currentWinValue + "3");
        add(currentWinSize);
        sliderWinSize = new JSlider(3, 10, 3);
        add(sliderWinSize);

        // Логика слайдеров: при движении ползунка меняется текст
        sliderFieldSize.addChangeListener(e -> {
            int val = sliderFieldSize.getValue();
            currentFieldSize.setText(currentFieldValue + val);
            // Ограничиваем макс. длину победы размером поля
            sliderWinSize.setMaximum(val);
        });

        sliderWinSize.addChangeListener(e ->
                currentWinSize.setText(currentWinValue + sliderWinSize.getValue())
        );

        // Кнопка старта
        btnStart = new JButton("Начать игру");
        btnStart.addActionListener(e -> {
            // Собираем данные из настроек
            int mode = humanVsAi.isSelected() ? 0 : 1;
            int size = sliderFieldSize.getValue();
            int win = sliderWinSize.getValue();

            // Вызываем метод запуска в главном окне
            gameWindow.startNewGame(mode, size, size, win);
            setVisible(false); // Скрываем окно настроек
        });

        add(btnStart);
    }
}