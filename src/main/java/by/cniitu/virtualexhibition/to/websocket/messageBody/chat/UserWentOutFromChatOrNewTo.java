package by.cniitu.virtualexhibition.to.websocket.messageBody.chat;

import by.cniitu.virtualexhibition.to.websocket.messageBody.MessageBody;
import by.cniitu.virtualexhibition.to.websocket.messageBody.chat.action.UserAction;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserWentOutFromChatOrNewTo extends MessageBody {

    private String chatId;
    private Integer userId;

    public UserWentOutFromChatOrNewTo(String chatId, Integer userId, UserAction userAction){
        super(userAction == UserAction.Exit ? "user went out" : "new user");
        this.chatId = chatId;
        this.userId = userId;
    }

    public UserWentOutFromChatOrNewTo(UserWentOutFromChatOrNewTo userWentOutFromChatOrNewTo, UserAction userAction){
        this(userWentOutFromChatOrNewTo.chatId, userWentOutFromChatOrNewTo.userId, userAction);
    }

    @Override
    public String toString() {
        return "{" +
                "\"messageDesc\": \"" + getMessageDesc() + "\"" +
                ", \"chatId\": \"" + chatId + "\"" +
                ", \"userId\": " + userId +
                '}';
    }

}
