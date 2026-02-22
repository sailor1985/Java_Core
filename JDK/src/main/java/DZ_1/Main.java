package DZ_1;

public class Main {
    public static void main(String[] args) {
        // Создаем сервер и сохраняем ссылку на него
        ServerWindow serverWindow = new ServerWindow();
        // Передаем ссылку на сервер в конструктор клиента
        new ClientGUI(serverWindow);
        new ClientGUI(serverWindow);
    }
}