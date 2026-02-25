package DZ_2.Task_1.Programm__interfaces;


import DZ_2.Task_1.Programm__interfaces.Client.ClientGUI;
import DZ_2.Task_1.Programm__interfaces.Server.ServerGUI;

public class Main {
    static void main(String[] args) {
        // Создаем сервер и сохраняем ссылку на него
        ServerGUI serverGUI = new ServerGUI();
        // Передаем ссылку на сервер в конструктор клиента
        new ClientGUI(serverGUI);
        new ClientGUI(serverGUI);
    }
}