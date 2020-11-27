package by.cniitu.virtualexhibition.to.websocket.messageBody.chat;

import by.cniitu.virtualexhibition.to.websocket.messageBody.MessageBody;
import by.cniitu.virtualexhibition.to.websocket.messageBody.chat.action.ChatAction;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NewToClientOrDestroyedChatTo extends MessageBody {

    private String chatId;

    public NewToClientOrDestroyedChatTo(String charId, ChatAction chatAction){
        super(chatAction == ChatAction.Create ? "new chat" : "chat destroyed");
        this.chatId = charId;
    }

    @Override
    public String toString() {
        return "{" +
                "\"messageDesc\": \"" + getMessageDesc() + "\"" +
                ", \"chatId\": \"" + chatId + "\"" +
                '}';
    }

}
