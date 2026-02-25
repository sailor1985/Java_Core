package DZ_2.Task_1.Programm__interfaces.Client;

import DZ_2.Task_1.Programm__interfaces.Server.Server;
import lombok.Getter;

//Логика работы
public class Client {
    private final Server server;// Ссылка на сервер
    private final ClientView view;
    boolean connected;
    @Getter
    private String name;
    @Getter
    private final String[] users = {"Ivan", "Masha", "Petr"};


    public Client(ClientView view, Server server) {
        this.view=view;
        this.server=server;
    }

    public void connectToServer(String name) {
        this.name=name;
        if (server.connectUser(this)) {
            connected = true;
            view.connectedToServer();
            printText ("Вы успешно подключились!\n");
        } else {
            printText("Подключение не удалось");
        }
    }

    public void disconnect() {
        if (connected) {
            connected = false;
            server.disconnectUser(this);  // Сообщаем серверу, что мы ушли
            view.disconnectFromServer(); // Метод в GUI для скрытия панелей или кнопок
            printText("Вы были отключены от сервера!");
        }
    }

    //Мы посылаем сообщение (через сервер)
    public void sendMessageToServer(String message) {
        if (connected) {
            if (!message.isEmpty()) {
                server.sendMessageToClient(name + ": " + message);
            } else {
                printText("Нет подключения к серверу");
            }
        }
    }

    //Нам посылают сообщение сервер
    public void serverAnswer(String answer) {
        printText(answer);
    }

    private void printText (String text) {
        view.sendClientLogMessage(text);
    }
}
