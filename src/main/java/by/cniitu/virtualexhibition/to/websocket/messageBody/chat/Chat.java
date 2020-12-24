package by.cniitu.virtualexhibition.to.websocket.messageBody.chat;

import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@ToString
public class Chat {

    public static final Set<Chat> chats = new HashSet<>();

    private String id;
    private Set<Integer> userIds;

    public Chat(){

    }

    public String getId() {
        return id;
    }

    public Set<Integer> getUserIds() {
        return userIds;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserIds(Set<Integer> userIds) {
        this.userIds = userIds;
    }

}
