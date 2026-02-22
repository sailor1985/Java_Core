package Sem_1_Graphical_interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

// Наследуемся от JPanel для рисования
public class Map extends JPanel {
    private static final Random RANDOM = new Random();
    private static final int HUMAN_DOT = 1;
    private static final int AI_DOT = 2;
    private static final int EMPTY_DOT = 0;
    private static final int PADDING = 10;

    private int gameStateType;
    private static final int STATE_GAME = 0;
    private static final int STATE_WIN_HUMAN = 1;
    private static final int STATE_WIN_AI = 2;
    private static final int STATE_DRAW = 3;

    private int fieldSizeX, fieldSizeY, winLen;
    private int[][] field;
    private int cellWidth, cellHeight;
    private boolean isGameWork;

    Map() {
        setBackground(Color.WHITE);
        // Слушатель кликов мыши
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (isGameWork) {
                    update(e);
                }
            }
        });
    }

    // Инициализация новой игры
    void startNewGame(int mode, int sizeX, int sizeY, int winLen) {
        this.fieldSizeX = sizeX;
        this.fieldSizeY = sizeY;
        this.winLen = winLen;
        field = new int[sizeY][sizeX];
        isGameWork = true;
        gameStateType = STATE_GAME;
        repaint(); // Перерисовать компонент
    }

    private void update(MouseEvent e) {
        // Вычисляем, в какую ячейку кликнули
        int x = e.getX() / cellWidth;
        int y = e.getY() / cellHeight;

        if (!isValidCell(x, y) || !isEmptyCell(x, y)) return;

        field[y][x] = HUMAN_DOT;
        if (checkEndGame(HUMAN_DOT, STATE_WIN_HUMAN)) return;

        aiTurn();
        repaint();
        checkEndGame(AI_DOT, STATE_WIN_AI);
    }

    private boolean isValidCell(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    private boolean isEmptyCell(int x, int y) {
        return field[y][x] == EMPTY_DOT;
    }

    private void aiTurn() {
        int x, y;
        do {
            x = RANDOM.nextInt(fieldSizeX);
            y = RANDOM.nextInt(fieldSizeY);
        } while (!isEmptyCell(x, y));
        field[y][x] = AI_DOT;
    }

    private boolean checkEndGame(int dot, int state) {
        if (checkWin(dot)) {
            gameStateType = state;
            isGameWork = false;
            repaint();
            return true;
        }
        if (isMapFull()) {
            gameStateType = STATE_DRAW;
            isGameWork = false;
            repaint();
            return true;
        }
        return false;
    }

    private boolean isMapFull() {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (field[i][j] == EMPTY_DOT) return false;
            }
        }
        return true;
    }

    private boolean checkWin(int dot) {
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY; j++) {
                if (checkLine(i, j, 1, 0, winLen, dot)) return true;
                if (checkLine(i, j, 0, 1, winLen, dot)) return true;
                if (checkLine(i, j, 1, 1, winLen, dot)) return true;
                if (checkLine(i, j, 1, -1, winLen, dot)) return true;
            }
        }
        return false;
    }

    private boolean checkLine(int x, int y, int vx, int vy, int len, int dot) {
        if (x + (len - 1) * vx >= fieldSizeX || y + (len - 1) * vy >= fieldSizeY || y + (len - 1) * vy < 0) return false;
        for (int i = 0; i < len; i++) {
            if (field[y + i * vy][x + i * vx] != dot) return false;
        }
        return true;
    }

    // Основной метод отрисовки
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!isGameWork && field == null) return;

        render(g);
    }

    private void render(Graphics g) {
        cellWidth = getWidth() / fieldSizeX;
        cellHeight = getHeight() / fieldSizeY;

        // Рисуем сетку
        g.setColor(Color.BLACK);
        for (int i = 0; i <= fieldSizeX; i++) {
            g.drawLine(i * cellWidth, 0, i * cellWidth, getHeight());
        }
        for (int i = 0; i <= fieldSizeY; i++) {
            g.drawLine(0, i * cellHeight, getWidth(), i * cellHeight);
        }

        // Рисуем Х и О
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (field[y][x] == HUMAN_DOT) {
                    g.setColor(Color.BLUE);
                    g.drawLine(x * cellWidth + PADDING, y * cellHeight + PADDING,
                            (x + 1) * cellWidth - PADDING, (y + 1) * cellHeight - PADDING);
                    g.drawLine(x * cellWidth + PADDING, (y + 1) * cellHeight - PADDING,
                            (x + 1) * cellWidth - PADDING, y * cellHeight + PADDING);
                } else if (field[y][x] == AI_DOT) {
                    g.setColor(Color.RED);
                    g.drawOval(x * cellWidth + PADDING, y * cellHeight + PADDING,
                            cellWidth - PADDING * 2, cellHeight - PADDING * 2);
                }
            }
        }

        if (gameStateType != STATE_GAME) showMessage(g);
    }

    private void showMessage(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, getHeight() / 2 - 40, getWidth(), 80);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        String msg = switch (gameStateType) {
            case STATE_DRAW -> "НИЧЬЯ!";
            case STATE_WIN_HUMAN -> "ИГРОК ПОБЕДИЛ!";
            case STATE_WIN_AI -> "ИИ ПОБЕДИЛ!";
            default -> "";
        };
        // Центрирование текста (упрощенно)
        g.drawString(msg, getWidth() / 4, getHeight() / 2 + 10);
    }
}