package DZ_2.Task_1.Programm__interfaces.Client;

public interface ClientView {
    void sendClientLogMessage(String message);// Метод, который вызовет сервер, чтобы передать клиенту сообщение
    void disconnectFromServer();
    void connectedToServer();
    void sendMessageToServer();// Метод при нажатии кнопки "Send"
}
