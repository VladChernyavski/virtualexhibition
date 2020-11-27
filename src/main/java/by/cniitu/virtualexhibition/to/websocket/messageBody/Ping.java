package by.cniitu.virtualexhibition.to.websocket.messageBody;

import by.cniitu.virtualexhibition.to.websocket.messageBody.chat.action.PingPongAction;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Ping extends MessageBody{

    public Ping(PingPongAction pingPongAction){
        super(pingPongAction == PingPongAction.Ping? "ping" : "pong");
    }

    @Override
    public String toString() {
        return "{" +
                "\"messageDesc\": \"" + getMessageDesc() + "\"" +
                '}';
    }

}
