package by.cniitu.virtualexhibition.to.websocket;

import by.cniitu.virtualexhibition.to.websocket.messageBody.MessageBody;
import by.cniitu.virtualexhibition.to.websocket.messageBody.Ping;
import by.cniitu.virtualexhibition.to.websocket.messageBody.chat.*;
import by.cniitu.virtualexhibition.to.websocket.messageBody.coordinates.CoordinatesToClient;
import by.cniitu.virtualexhibition.to.websocket.messageBody.coordinates.CoordinatesToServer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

// TODO make all messages as shorter as possible

@Getter
@NoArgsConstructor
@JsonDeserialize(using = SocketToDeserializer.class)
@AllArgsConstructor
public class SocketTo {

    static final Map<Class, Integer> classToMessageType = new HashMap<>();

    static{
        classToMessageType.put(CoordinatesToServer.class, 0);
        classToMessageType.put(CoordinatesToClient.class, 0);
        classToMessageType.put(NewChatToServer.class, 2);
        classToMessageType.put(NewToClientOrDestroyedChatTo.class, 3);
        classToMessageType.put(MessageTo.class, 1);
        classToMessageType.put(UserWentOutFromChatOrNewTo.class, 4);
        classToMessageType.put(UserWentOutToClient.class, 5);
        classToMessageType.put(Ping.class, 6);
    }

    private Integer messageType;

    private MessageBody messageBody;

    public SocketTo(MessageBody messageBody) throws Exception{
        this.messageType = classToMessageType.get(messageBody.getClass());
        if(this.messageType == null)
            throw new Exception("no messageType found for such messageBody!");
        this.messageBody = messageBody;
    }

    @Override
    public String toString() {
        return "{" +
                "\"messageType\": " + messageType +
                ", \"messageBody\": " + messageBody +
                '}';
    }
}
