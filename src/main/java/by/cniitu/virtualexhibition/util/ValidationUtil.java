package by.cniitu.virtualexhibition.util;

import by.cniitu.virtualexhibition.entity.user.User;

public class ValidationUtil {

    private static final String EMAIL_PATTERN = "[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";
    private static final String FIRSTNAME_PATTERN = "[A-Za-zА-Яа-я]{2,20}+";
    private static final String LASTNAME_PATTERN = "[A-Za-zА-Яа-я\\-]{2,32}+";

    //пароль должен содежрать как минимум одну латинскую букву любого регистра и одну цифру,
    // быть не менне 8 символов и не более 32 и не содержать других символов, кроме "_"
    private static final String PASSWORD_PATTERN = "(?=.*[a-zA-Z])(?=.*[0-9])(?=\\w+$).{8,32}";
    private static final String NICKNAME_PATTERN = "[a-zA-Z0-9А-Яа-я\\_]{3,20}";

    public static boolean isUserValid(User user) {
        if (!isEmailValid(user.getEmail())) {
            System.out.println("EMAIL NOT VALID");
            return false;
        }
        if (!isPasswordValid(user.getPassword())) {
            System.out.println("PASSWORD NOT VALID");
            return false;
        }
        if (!isFirstNameValid(user.getFirstName())) {
            System.out.println("FIRSTNAME NOT VALID");
            return false;
        }
        if (!isLastNameValid(user.getLastName())) {
            System.out.println("LASTNAME NOT VALID");
            return false;
        }
        if (!isNickNameValid(user.getNickName())) {
            System.out.println("NICKNAME NOT VALID");
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
