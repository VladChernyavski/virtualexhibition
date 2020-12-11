package by.cniitu.virtualexhibition.to.websocket.messageBody;

import by.cniitu.virtualexhibition.to.websocket.messageBody.chat.action.PingPongAction;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
public class Ping extends MessageBody{

    static Map<PingPongAction, String> pingPongActionMap = new HashMap<>();

    static{
        pingPongActionMap.put(PingPongAction.Ping, "ping");
        pingPongActionMap.put(PingPongAction.Pong, "pong");
        pingPongActionMap.put(PingPongAction.Error, "error");
    }

    private Integer count;

    public Ping(PingPongAction pingPongAction, Integer count){
        super(pingPongActionMap.get(pingPongAction));
        this.count = count;
    }

    @Override
    public String toString() {
        return "{" +
                "\"messageDesc\": \"" + getMessageDesc() + "\"" +
                "\"count\": " + count +
                '}';
    }

}
