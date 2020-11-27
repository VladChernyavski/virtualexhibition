package by.cniitu.virtualexhibition.to.websocket.messageBody.chat;

import by.cniitu.virtualexhibition.to.websocket.messageBody.MessageBody;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserWentOutToClient extends MessageBody {

    private Integer userId;

    public UserWentOutToClient(Integer userId){
        super("user went out");
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "{" +
                "\"messageDesc\": \"" + getMessageDesc() + "\"" +
                ", \"userId\": " + userId +
                '}';
    }

}
