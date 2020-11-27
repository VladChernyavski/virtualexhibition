package by.cniitu.virtualexhibition.to.websocket.messageBody.chat;

import org.java_websocket.WebSocket;

import java.util.HashSet;
import java.util.Set;

public class Chat {

    public static final Set<Chat> chats = new HashSet<>();

    private String id;
    private Set<WebSocket> webSockets;

    public Chat(){

    }

    public String getId() {
        return id;
    }

    public Set<WebSocket> getWebSockets() {
        return webSockets;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setWebSockets(Set<WebSocket> webSockets) {
        this.webSockets = webSockets;
    }

}
