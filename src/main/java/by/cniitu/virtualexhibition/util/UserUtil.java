package by.cniitu.virtualexhibition.util;

import by.cniitu.virtualexhibition.entity.user.User;
import by.cniitu.virtualexhibition.entity.user.UserAction;
import by.cniitu.virtualexhibition.to.UserActionTo;
import by.cniitu.virtualexhibition.to.websocket.UserTo;
import org.java_websocket.WebSocket;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserUtil {

    public static final Map<Integer, Map<WebSocket, UserTo>> exhibitionWithWebsocketAndUser = new ConcurrentHashMap<>();
    public static final Map<Integer, WebSocket> userIdWithWebsocket = new ConcurrentHashMap<>();

    public static User prepareToSave(User user, PasswordEncoder passwordEncoder) {
        String password = user.getPassword();
        user.setPassword(StringUtils.hasText(password) ? passwordEncoder.encode(password) : password);
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public static User getUserByParseCode(String code) {
        byte[] decoded = Base64.getDecoder().decode(code);
        String decodedString = new String(decoded);

        String[] data = decodedString.split("~");
        User user = new User();
        user.setFirstName(data[0]);
        user.setLastName(data[1]);
        user.setNickName(data[2]);
        user.setEmail(data[3]);
        user.setPassword(data[4]);

        return user;
    }

    public static UserActionTo toUserActionTo(UserAction action){
        return new UserActionTo(action.getId(), action.getFile().getFileType().getName(), action.getActionType().getName(),
                action.getAction_time().toLocalDateTime().format(DateTimeFormatter.ofPattern("dd:MM:yyyy hh:mm")));
    }

}
