package DZ_2.Task_1.Programm__interfaces.Server;

import DZ_2.Task_1.Programm__interfaces.Client.Client;
import DZ_2.Task_1.Programm__interfaces.Repository.Repository;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Server {
    private ServerView view;
    private boolean isServerWorking;
    private final List<Client> clients = new ArrayList<>(); // Список подключенных клиентов
    private static final String CONNECTED = " подключился к беседе";
    private Repository repository;

    public Server(ServerView view) {
        this.view = view;
        this.repository = new Repository(view);
    }

    public void sendMessageToClient(String message) {
        if (!isServerWorking) return;
        view.sendServerLogMessage(message + "\n");
        repository.saveLogToFile(message);// СОХРАНЯЕМ в файл каждое сообщение
        broadcastMessage(message);
    }

    private void broadcastMessage(String message) {
        for (Client client : clients) {
            client.serverAnswer(message);
        }
    }

    public boolean connectUser(Client client) {
        if (isServerWorking) {
            clients.add(client);
            view.sendServerLogMessage(client.getName() + CONNECTED + "\n");

            // ПРИ ПОДКЛЮЧЕНИИ: отправляем клиенту всю историю из файла
            String history = repository.readLogFromFile();
            client.serverAnswer(history);
            return true;//Если сервер работает
        }
        return false;//Если выключен
    }

    public void disconnectUser(Client client) {
        if (client != null) {
            clients.remove(client);
            view.sendServerLogMessage(client.getName() + " отключился от беседы");
            broadcastMessage(client.getName() + " покинул чат."); //Оповещаем остальных участников, что кто-то ушел
        }
    }
}