package by.cniitu.virtualexhibition.util;

import by.cniitu.virtualexhibition.entity.user.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.util.Base64;

public class UserUtil {

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

}
