package by.cniitu.virtualexhibition.to.websocket.messageBody.chat;

import by.cniitu.virtualexhibition.to.websocket.messageBody.MessageBody;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Getter
@NoArgsConstructor
public class NewChatToServer extends MessageBody {

    private List<Integer> userIds;

    public NewChatToServer(List<Integer> userIds){
        super("new char");
        this.userIds = new LinkedList<>(userIds);
    }

    @Override
    public String toString() {
        return "{" +
                "\"messageDesc\": \"" + getMessageDesc() + "\"" +
                ", \"userIds\": " + userIds +
                '}';
    }

}
