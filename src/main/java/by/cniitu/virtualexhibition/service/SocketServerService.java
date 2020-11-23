package by.cniitu.virtualexhibition.service;

import by.cniitu.virtualexhibition.service.mock.SendCoordinate;
import by.cniitu.virtualexhibition.to.websocket.Chat;
import by.cniitu.virtualexhibition.to.websocket.UserTo;
import by.cniitu.virtualexhibition.util.JsonUtil;
import by.cniitu.virtualexhibition.util.UserUtil;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SocketServerService extends WebSocketServer {

    public SocketServerService(@Value("${websocket.port}") int port) {
        super(new InetSocketAddress(port));
    }

    @PostConstruct
    public void startServerSocket() {
        this.start();
    }

    public void onStart() {
        System.out.println("Socket onStart");
        new SendCoordinate(this).start();
    }

    /**
     * Called when new client connects
     */
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        System.out.println("Socket onOpen " + webSocket);

    }

    /**
     * Called on client disconnect
     */
    public void onClose(WebSocket webSocket, int code, String reason, boolean remote) {
        System.out.println("Socket onClose " + webSocket);

        for (Map.Entry<Integer, Map<WebSocket, UserTo>> mapEntry : UserUtil.exhibitionWithWebsocketAndUser.entrySet()) {
            mapEntry.getValue().keySet().remove(webSocket);
        }

        Map.Entry<Integer, WebSocket> integerWebSocketEntry = UserUtil.userIdWithWebsocket.entrySet().stream()
                .filter(ws -> ws.getValue().equals(webSocket))
                .findFirst()
                .orElse(null);

        if (!Objects.isNull(integerWebSocketEntry)) {
            Integer userId = integerWebSocketEntry.getKey();
            UserUtil.userIdWithWebsocket.remove(userId);

            broadcast("We have disconnected client: " + userId);
        }
        // Just for example, send information to all clients
        // TODO обработать на клиенте ( возвращать id)

    }

    /**
     * Called when client sends message
     */
    public void onMessage(WebSocket webSocket, String s) {
        System.out.println(String.format("Socket %s onMessage %s", webSocket, s));

        //user coordinate, exhibition id, user id ("move~json user")
        if (s.startsWith("move~")) {
            sendMove(webSocket, s);
        }

        // users id ("initchat~123&124&434")
        if (s.startsWith("initchat~")) {
            initChat(webSocket, s);
        }

        // chat id ("chat~id~message")
        if (s.startsWith("chat~")) {
            sendMessage(webSocket, s);
        }

        // ("destroychat~id")
        if (s.startsWith("destroychat~")) {
            destroyChat(s);
        }

        // ("exitchat~chatid" or "exitchat~chatid~userid)
        if (s.startsWith("exitchat~")) {
            exitFromChat(webSocket, s);
        }

        // ("addtochat~chatid~userid")
        if (s.startsWith("addtochat~")) {
            addToChat(s);
        }
    }

    public void onError(WebSocket webSocket, Exception e) {
        System.out.println(String.format("Socket %s onError %s", webSocket, e));
    }

    private void sendMove(WebSocket webSocket, String message) {
        String[] strings = message.split("~");

        UserTo user = JsonUtil.getUserToByJson(strings[1]);
        if (Objects.isNull(user)) {
            return;
        }

        int exhibitionId = user.getExhibId();

        //if user first time invoke move - flag is true
        boolean flag = false;
        if (!UserUtil.userIdWithWebsocket.containsKey(user.getUserId())) {
            UserUtil.userIdWithWebsocket.put(user.getUserId(), webSocket);
            flag = true;
        }

        Map<Integer, Map<WebSocket, UserTo>> exhibitionWithWebsocketAndUser = UserUtil.exhibitionWithWebsocketAndUser;
        if (exhibitionWithWebsocketAndUser.containsKey(exhibitionId)) {
            exhibitionWithWebsocketAndUser.get(exhibitionId).put(webSocket, user);
        } else {
            exhibitionWithWebsocketAndUser.put(exhibitionId, new ConcurrentHashMap<WebSocket, UserTo>() {{
                put(webSocket, user);
            }});
        }

        if (flag) {
            for (UserTo u : UserUtil.exhibitionWithWebsocketAndUser.get(exhibitionId).values()) {
                if(user.equals(u)){
                    continue;
                }
                webSocket.send(JsonUtil.getJsonString(u));
            }
        }

        broadcast(JsonUtil.getJsonString(user), UserUtil.exhibitionWithWebsocketAndUser.get(exhibitionId).keySet());
    }

    private void initChat(WebSocket webSocket, String message) {
        String[] strings = message.split("~");

        Chat chat = new Chat();
        chat.setWebSockets(new HashSet<WebSocket>()
//        {{
//            add(webSocket);
//        }}
        );
        String chatId = UUID.randomUUID().toString();
        chat.setId(chatId);
        System.out.println("Chat ID: " + chatId);
        String[] userIds = strings[1].split("&");
        for (String s : userIds) {
            int id = Integer.parseInt(s);
            chat.getWebSockets().add(UserUtil.userIdWithWebsocket.get(id));
        }
        Chat.chats.add(chat);
        //send chat id all users from chat
        broadcast("new chat~" + chat.getId(), chat.getWebSockets());
    }

    private void sendMessage(WebSocket webSocket, String message) {
        String[] strings = message.split("~");
        String chatId = strings[1];
        String messageToUsers = strings[2];

        Chat chat = Chat.chats.stream()
                .filter(c -> c.getId().equalsIgnoreCase(chatId))
                .findFirst().orElse(null);

        if (Objects.isNull(chat)) {
            return;
        }

        Map.Entry<Integer, WebSocket> integerWebSocketEntry = UserUtil.userIdWithWebsocket.entrySet().stream()
                .filter(ws -> ws.getValue().equals(webSocket))
                .findFirst()
                .orElse(null);
        if (Objects.isNull(integerWebSocketEntry)) {
            return;
        }
        int userId = integerWebSocketEntry.getKey();

        broadcast("new message~" + chatId + "~" + userId + "~" + messageToUsers, chat.getWebSockets());
    }

    private void destroyChat(String message) {
        String[] strings = message.split("~");
        String chatId = strings[1];

        Chat chat = Chat.chats.stream()
                .filter(c -> c.getId().equals(chatId))
                .findFirst().orElse(null);

        if (Objects.isNull(chat)) {
            return;
        }

        Chat.chats.remove(chat);
    }

    private void exitFromChat(WebSocket webSocket, String message) {
        String[] strings = message.split("~");
        String chatId = strings[1];

        Chat chat = Chat.chats.stream()
                .filter(c -> c.getId().equals(chatId))
                .findFirst().orElse(null);

        if (Objects.isNull(chat)) {
            return;
        }

        int userId;
        if (strings.length > 2) {
            userId = Integer.parseInt(strings[2]);
            chat.getWebSockets().remove(UserUtil.userIdWithWebsocket.get(userId));
        } else {
            chat.getWebSockets().remove(webSocket);
            Map.Entry<Integer, WebSocket> integerWebSocketEntry = UserUtil.userIdWithWebsocket.entrySet().stream()
                    .filter(ws -> ws.getValue().equals(webSocket))
                    .findFirst()
                    .orElse(null);
            if (Objects.isNull(integerWebSocketEntry)) {
                return;
            }
            userId = integerWebSocketEntry.getKey();
        }

        broadcast("User " + userId + " leave chat", chat.getWebSockets());
    }

    private void addToChat(String message) {
        String[] strings = message.split("~");
        String chatId = strings[1];
        int userId = Integer.parseInt(strings[2]);

        Chat chat = Chat.chats.stream()
                .filter(c -> c.getId().equals(chatId))
                .findFirst().orElse(null);

        if (Objects.isNull(chat)) {
            return;
        }

        broadcast("Added new user " + userId, chat.getWebSockets());

        chat.getWebSockets().add(UserUtil.userIdWithWebsocket.get(userId));
    }
}
