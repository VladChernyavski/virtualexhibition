package by.cniitu.virtualexhibition;

import by.cniitu.virtualexhibition.to.websocket.SocketTo;
import by.cniitu.virtualexhibition.to.websocket.messageBody.chat.*;
import by.cniitu.virtualexhibition.to.websocket.messageBody.chat.action.ChatAction;
import by.cniitu.virtualexhibition.to.websocket.messageBody.chat.action.UserAction;
import by.cniitu.virtualexhibition.to.websocket.messageBody.coordinates.CoordinatesToClient;
import by.cniitu.virtualexhibition.to.websocket.messageBody.coordinates.CoordinatesToServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class VirtualExhibitionApplication {

    public static void main(String[] args) {

        // TODO cancel this shit
        try {
            System.out.println("MessageTo");
            System.out.println(new SocketTo(new MessageTo(1, "qw", "er", Arrays.asList(2, 3))));
            System.out.println("NewChatToServer");
            System.out.println(new SocketTo(new NewChatToServer(Arrays.asList(4, 5))));
            System.out.println("NewOrDestroyedChatToClient Create");
            System.out.println(new SocketTo(new NewToClientOrDestroyedChatTo("ty", ChatAction.Create)));
            System.out.println("NewOrDestroyedChatToClient Destroy");
            System.out.println(new SocketTo(new NewToClientOrDestroyedChatTo("ui", ChatAction.Destroy)));
            System.out.println("UserWentOutFromChatOrNewTo Enter");
            System.out.println(new SocketTo(new UserWentOutFromChatOrNewTo("op", 6, UserAction.Enter)));
            System.out.println("UserWentOutFromChatOrNewTo Enter");
            System.out.println(new SocketTo(new UserWentOutFromChatOrNewTo("as", 7, UserAction.Exit)));
            System.out.println("UserWentOutToClient");
            System.out.println(new SocketTo(new UserWentOutToClient(8)));
            System.out.println("CoordinatesToClient");
            System.out.println(new SocketTo(new CoordinatesToClient(9, 10.11,  14.15, "#123456")));
            System.out.println("CoordinatesToServer");
            System.out.println(new SocketTo(new CoordinatesToServer(16, 17.18, 19.20, 21.22,"#7890ab", 23)));
        } catch (Exception ex){
            ex.printStackTrace();
        }

        SpringApplication.run(VirtualExhibitionApplication.class, args);
    }

}
