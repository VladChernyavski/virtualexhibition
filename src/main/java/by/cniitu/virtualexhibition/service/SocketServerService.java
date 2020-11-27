package by.cniitu.virtualexhibition.service;

import by.cniitu.virtualexhibition.service.mock.SendCoordinate;
import by.cniitu.virtualexhibition.to.websocket.SocketTo;
import by.cniitu.virtualexhibition.to.websocket.messageBody.MessageBody;
import by.cniitu.virtualexhibition.to.websocket.messageBody.Ping;
import by.cniitu.virtualexhibition.to.websocket.messageBody.chat.*;
import by.cniitu.virtualexhibition.to.websocket.messageBody.chat.action.ChatAction;
import by.cniitu.virtualexhibition.to.websocket.messageBody.chat.action.PingPongAction;
import by.cniitu.virtualexhibition.to.websocket.messageBody.chat.action.UserAction;
import by.cniitu.virtualexhibition.to.websocket.messageBody.coordinates.CoordinatesToClient;
import by.cniitu.virtualexhibition.to.websocket.messageBody.coordinates.CoordinatesToServer;
import by.cniitu.virtualexhibition.util.JsonUtil;
import by.cniitu.virtualexhibition.util.UserUtil;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.*;
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
        // we do not send the coordinates of other users to new one because we do not know the id of the exhibition
    }

    /**
     * Called on client disconnect
     */
    public void onClose(WebSocket webSocket, int code, String reason, boolean remote) {
        System.out.println("Socket onClose " + webSocket);

        // cancel user from the exhibition
        for (Map.Entry<Integer, Map<WebSocket, CoordinatesToClient>> mapEntry : UserUtil.exhibitionWithWebsocketAndUser.entrySet()) {
            mapEntry.getValue().keySet().remove(webSocket);
        }

        // cancel connection between webSocket and userId
        Map.Entry<Integer, WebSocket> integerWebSocketEntry = UserUtil.userIdWithWebsocket.entrySet().stream()
                .filter(ws -> ws.getValue().equals(webSocket))
                .findFirst()
                .orElse(null);
        if (!Objects.isNull(integerWebSocketEntry)) {
            Integer userId = integerWebSocketEntry.getKey();
            UserUtil.userIdWithWebsocket.remove(userId);
            try {
                broadcast(JsonUtil.getJsonString(new SocketTo(new UserWentOutToClient(userId))));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Called when client sends message
     */
    // TODO create a mapping from messageType to handlers
    public void onMessage(WebSocket webSocket, String s) {

        SocketTo socketTo = JsonUtil.getSocketToByJson(s);
        System.out.println("socketTo = " + socketTo);
        if(socketTo == null){
            // TODO is the message have an error disconnect the person forever
            webSocket.send("{\"message\": \"error\"}");
            return;
        }

        MessageBody messageBody = socketTo.getMessageBody();

        if (socketTo.getMessageType() == 0){ // user motion
            sendMove(webSocket, messageBody);
        }
        else if (socketTo.getMessageType() == 1){ // new message in a chat
            sendMessage(webSocket, messageBody);
        }
        else if (socketTo.getMessageType() == 2){ // new chat
            initChat(messageBody);
        }
        else if (socketTo.getMessageType() == 3){ // chat destroyed
            destroyChat(messageBody);
        }
        else if (socketTo.getMessageType() == 4){ // user went out or new user (chat)
            String messageDesc = messageBody.getMessageDesc();
            if(messageDesc.equals("user went out")){
                exitFromChat(messageBody);
            }
            else if(messageDesc.equals("new user")){
                addToChat(messageBody);
            }
        }
        else {
            try {
                webSocket.send(JsonUtil.getJsonString(new SocketTo(new Ping(PingPongAction.Ping))));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void onError(WebSocket webSocket, Exception e) {
        System.out.printf("Socket %s onError %s%n", webSocket, e);
    }

    // if some user moves we tall it to all other users
    private void sendMove(WebSocket webSocket, MessageBody messageBody) {
        CoordinatesToServer coordinatesToServer = (CoordinatesToServer)messageBody;
        int exhibitionId = coordinatesToServer.getExhibitId();

        if (!UserUtil.userIdWithWebsocket.containsKey(coordinatesToServer.getUserId())) { // if user first time invoke move
            UserUtil.userIdWithWebsocket.put(coordinatesToServer.getUserId(), webSocket);

            // sent to hem all other coordinates from this exhibition
            for (CoordinatesToClient u : UserUtil.exhibitionWithWebsocketAndUser.get(exhibitionId).values()) {
                webSocket.send(JsonUtil.getJsonString(u));
            }
        }

        if (UserUtil.exhibitionWithWebsocketAndUser.containsKey(exhibitionId)) {
            UserUtil.exhibitionWithWebsocketAndUser.get(exhibitionId).put(webSocket, coordinatesToServer);
        } else {
            UserUtil.exhibitionWithWebsocketAndUser.put(exhibitionId,
                    new ConcurrentHashMap<WebSocket, CoordinatesToClient>() {{
                put(webSocket, coordinatesToServer);
            }});
        }

        try {
            broadcast(JsonUtil.getJsonString(new SocketTo(coordinatesToServer)),
                    UserUtil.exhibitionWithWebsocketAndUser.get(exhibitionId).keySet());
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void initChat(MessageBody messageBody) {
        NewChatToServer newChatToServer = (NewChatToServer) messageBody;
        Chat chat = new Chat();
        chat.setWebSockets(new HashSet<>());
        String chatId = UUID.randomUUID().toString();
        chat.setId(chatId);
        System.out.println("Chat ID: " + chatId);
        for (Integer id : newChatToServer.getUserIds()) {
            chat.getWebSockets().add(UserUtil.userIdWithWebsocket.get(id));
        }
        Chat.chats.add(chat);

        //send chat id to all users from chat
        try {
            broadcast(JsonUtil.getJsonString(new SocketTo(new NewToClientOrDestroyedChatTo(chatId, ChatAction.Create))), chat.getWebSockets());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(WebSocket webSocket, MessageBody messageBody) {
        MessageTo messageTo = (MessageTo) messageBody;

        Chat chat = Chat.chats.stream()
                .filter(c -> c.getId().equalsIgnoreCase(messageTo.getChatId()))
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

        try {
            broadcast(JsonUtil.getJsonString(new SocketTo(new MessageTo(messageTo))), chat.getWebSockets());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void destroyChat(MessageBody messageBody) {
        NewToClientOrDestroyedChatTo newToClientOrDestroyedChatTo = (NewToClientOrDestroyedChatTo) messageBody;
        String chatId = newToClientOrDestroyedChatTo.getChatId();

        Chat chat = Chat.chats.stream()
                .filter(c -> c.getId().equals(chatId))
                .findFirst().orElse(null);

        if (Objects.isNull(chat)) {
            return;
        }

        try {
            broadcast(JsonUtil.getJsonString(new SocketTo(new NewToClientOrDestroyedChatTo(chatId, ChatAction.Destroy))),
                    chat.getWebSockets());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Chat.chats.remove(chat);
    }

    private void exitFromChat(MessageBody messageBody) {
        UserWentOutFromChatOrNewTo userWentOutFromChatOrNewTo = (UserWentOutFromChatOrNewTo) messageBody;
        Chat chat = Chat.chats.stream()
                .filter(c -> c.getId().equals(userWentOutFromChatOrNewTo.getChatId()))
                .findFirst().orElse(null);
        if (Objects.isNull(chat)) {
            return;
        }
        //if (strings.length > 2) {

        chat.getWebSockets().remove(UserUtil.userIdWithWebsocket.get(userWentOutFromChatOrNewTo.getUserId()));

        // users were able to exit without giving an id
        /*} else {
            chat.getWebSockets().remove(webSocket);
            Map.Entry<Integer, WebSocket> integerWebSocketEntry = UserUtil.userIdWithWebsocket.entrySet().stream()
                    .filter(ws -> ws.getValue().equals(webSocket))
                    .findFirst()
                    .orElse(null);
            if (Objects.isNull(integerWebSocketEntry)) {
                return;
            }
            userId = integerWebSocketEntry.getKey();
        }*/

        try {
            broadcast(JsonUtil.getJsonString(new SocketTo(new UserWentOutFromChatOrNewTo(userWentOutFromChatOrNewTo,
                    UserAction.Exit))), chat.getWebSockets());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addToChat(MessageBody messageBody) {

        UserWentOutFromChatOrNewTo userWentOutFromChatOrNewTo = (UserWentOutFromChatOrNewTo) messageBody;

        Chat chat = Chat.chats.stream()
                .filter(c -> c.getId().equals(userWentOutFromChatOrNewTo.getChatId()))
                .findFirst().orElse(null);

        if (Objects.isNull(chat)) {
            return;
        }

        chat.getWebSockets().add(UserUtil.userIdWithWebsocket.get(userWentOutFromChatOrNewTo.getUserId()));

        try {
            broadcast(JsonUtil.getJsonString(new SocketTo(new UserWentOutFromChatOrNewTo(userWentOutFromChatOrNewTo,
                    UserAction.Enter))), chat.getWebSockets());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
