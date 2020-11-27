package by.cniitu.virtualexhibition.to.websocket;

import by.cniitu.virtualexhibition.to.websocket.messageBody.MessageBody;
import by.cniitu.virtualexhibition.to.websocket.messageBody.Ping;
import by.cniitu.virtualexhibition.to.websocket.messageBody.chat.*;
import by.cniitu.virtualexhibition.to.websocket.messageBody.coordinates.CoordinatesToServer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SocketToDeserializer extends StdDeserializer<SocketTo> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // here we have mapping only to those class that the server can receive
    static final Map<Integer, Class> messageTypeToClass = new HashMap<>();

    static{
        messageTypeToClass.put(0, CoordinatesToServer.class);
        messageTypeToClass.put(2, NewChatToServer.class);
        messageTypeToClass.put(3, NewToClientOrDestroyedChatTo.class);
        messageTypeToClass.put(1, MessageTo.class);
        messageTypeToClass.put(4, UserWentOutFromChatOrNewTo.class);
        messageTypeToClass.put(5, UserWentOutToClient.class);
        messageTypeToClass.put(6, Ping.class);
    }

    protected SocketToDeserializer() {
        super(SocketTo.class);
    }

    @Override
    public SocketTo deserialize(JsonParser jsonParser,
                                DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        Integer messageType = (Integer)node.get("messageType").numberValue();
        return new SocketTo(messageType,
                (MessageBody) objectMapper.readValue(
                        node.get("messageBody").toString(),
                        messageTypeToClass.get(messageType)
                )
        );
    }

}
