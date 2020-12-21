package by.cniitu.virtualexhibition.to.websocket.messageBody.chat;

import by.cniitu.virtualexhibition.to.websocket.messageBody.MessageBody;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Getter
@NoArgsConstructor
public class MessageTo extends MessageBody {

    private Integer userId;
    private String chatId;
    private String text;
    private List<FileWithSizeTo> filesWithSize;

    public MessageTo(Integer userId, String chatId, String text, List<FileWithSizeTo> fileIds){
        super("new message");
        this.userId = userId;
        this.chatId = chatId;
        this.text = text;
        this.filesWithSize = new LinkedList<>(fileIds);
    }

    public MessageTo(MessageTo messageTo){
        this(messageTo.userId, messageTo.chatId, messageTo.text, messageTo.filesWithSize);
    }

    @Override
    public String toString() {
        return "{" +
                "\"messageDesc\": \"" + getMessageDesc() + "\"" +
                ", \"userId\": " + userId +
                ", \"chatId\": \"" + chatId + "\"" +
                ", \"text\": \"" + text + "\"" +
                ", \"fileIds\": " + filesWithSize +
                '}';
    }


}
