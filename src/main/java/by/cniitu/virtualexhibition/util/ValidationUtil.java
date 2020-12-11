package by.cniitu.virtualexhibition.util;

import by.cniitu.virtualexhibition.entity.user.User;

public class ValidationUtil {

    private static final String EMAIL_PATTERN = "[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";
    private static final String FIRSTNAME_PATTERN = "[A-Za-z]{2,20}+";
    private static final String LASTNAME_PATTERN = "[A-Za-z\\-]{2,32}+";

    //пароль должен содежрать как минимум одну латинскую букву любого регистра и одну цифру,
    // быть не менне 8 символов и не более 32 и не содержать других символов, кроме "_"
    private static final String PASSWORD_PATTERN = "(?=.*[a-zA-Z])(?=.*[0-9])(?=\\w+$).{8,32}";
    private static final String NICKNAME_PATTERN = "[a-zA-Z0-9]{3,20}";

    public static boolean isUserValid(User user) {
        if (!isEmailValid(user.getEmail())) {
            return false;
        }
        if (!isPasswordValid(user.getPassword())) {
            return false;
        }
        if (!isFirstNameValid(user.getFirstName())) {
            return false;
        }
        if (!isLastNameValid(user.getLastName())) {
            return false;
        }
        if (!isNickNameValid(user.getNickName())) {
            return false;
        }
        return true;
    }

    //TODO private
    public static boolean isEmailValid(String email) {
        return email.matches(EMAIL_PATTERN);
    }

    public static boolean isPasswordValid(String password) {
        return password.matches(PASSWORD_PATTERN);
    }

    public static boolean isFirstNameValid(String firstName) {
        return firstName.matches(FIRSTNAME_PATTERN);
    }

    public static boolean isLastNameValid(String lastName) {
        return lastName.matches(LASTNAME_PATTERN);
    }

    public static boolean isNickNameValid(String nickName) {
        return nickName.matches(NICKNAME_PATTERN);
    }

}
