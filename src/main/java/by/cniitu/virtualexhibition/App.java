package by.cniitu.virtualexhibition;

import by.cniitu.virtualexhibition.util.ValidationUtil;
import by.cniitu.virtualexhibition.web.controller.file.FileUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class App {

    public static void main(String[] args) {

//        System.out.println(getString("a4 9f a9 42 n6 5d 46 23 a2 e1 66 c2 e0 36 bd 66"));
//
//        PasswordEncoder encoder = new BCryptPasswordEncoder();
//        System.out.println(encoder.encode("testtest"));

        System.out.println(FileUtil.getReadableFileSize(133));
        System.out.println(FileUtil.getReadableFileSize(67731872));
        System.out.println(FileUtil.getReadableFileSize(9831872));
        System.out.println(FileUtil.getReadableFileSize(31872));
        System.out.println(FileUtil.getReadableFileSize(1026518782));
        System.out.println(FileUtil.getReadableFileSize(2434423412344872L));

    }

    public static String getString(String string){
        String[] strings = string.split(" ");
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < strings.length; i++){
            stringBuilder.append("0x").append(strings[i]).append(", ");
        }

        String result = stringBuilder.toString();
        return result.substring(0, result.length() - 2);
    }

    public static void isEmailValid(){
        System.out.println(ValidationUtil.isEmailValid("abc123@gmail.com"));
        System.out.println(ValidationUtil.isEmailValid("vl.chert@.com"));
        System.out.println(ValidationUtil.isEmailValid("vladislav.chernavskyi@cniitu-it.by"));
        System.out.println(ValidationUtil.isEmailValid("idea-support@jetbrains.com"));
        System.out.println(ValidationUtil.isEmailValid("oz@oz.by"));
        System.out.println(ValidationUtil.isEmailValid("chernyavskivl@gmail.com"));
        System.out.println(ValidationUtil.isEmailValid("1julia_bez@mail.yp"));
        System.out.println(ValidationUtil.isEmailValid("no-reply@accounts.google.com"));
        System.out.println(ValidationUtil.isEmailValid("no-replym.mail.coursera.org"));
    }

    public static void isPasswordValid(){
        System.out.println(ValidationUtil.isPasswordValid("qwertyuiop")); //false
        System.out.println(ValidationUtil.isPasswordValid("qwertyu")); //false
        System.out.println(ValidationUtil.isPasswordValid("ryjurj145533")); //true
        System.out.println(ValidationUtil.isPasswordValid("Tffe6%fgh")); //false
        System.out.println(ValidationUtil.isPasswordValid("GTERA1234")); //true
        System.out.println(ValidationUtil.isPasswordValid("GTERA^1234")); //false
        System.out.println(ValidationUtil.isPasswordValid("GTE&RA1234")); //false
        System.out.println(ValidationUtil.isPasswordValid("GTEgdgdgdgf")); //false
        System.out.println(ValidationUtil.isPasswordValid("_123AE#Dfgh")); //false
    }

    public static void isFirstNameValid(){
        System.out.println(ValidationUtil.isFirstNameValid("Vlad"));
        System.out.println(ValidationUtil.isFirstNameValid("Vlad 3"));
        System.out.println(ValidationUtil.isFirstNameValid("Great Vlad"));
        System.out.println(ValidationUtil.isFirstNameValid("Vlad-Gen"));
    }

    public static void isLastNameValid(){
        System.out.println(ValidationUtil.isLastNameValid("Chernyavski")); //true
        System.out.println(ValidationUtil.isLastNameValid("eyrereygreg")); //true
        System.out.println(ValidationUtil.isLastNameValid("Chernyavski-CHer")); //true
        System.out.println(ValidationUtil.isLastNameValid("Chernyavski- CHer")); //false
        System.out.println(ValidationUtil.isLastNameValid("Chernyavski 123")); //false
        System.out.println(ValidationUtil.isLastNameValid("Chernyavski Great")); //false
        System.out.println(ValidationUtil.isLastNameValid("Chernyavski#")); //false
        System.out.println(ValidationUtil.isLastNameValid("Chern^fafa")); //false
    }

    public static void isNickNameValid(){
        System.out.println(ValidationUtil.isNickNameValid("Vlad1321")); //true
        System.out.println(ValidationUtil.isNickNameValid("Vladqwert")); //true
        System.out.println(ValidationUtil.isNickNameValid("G")); //false
        System.out.println(ValidationUtil.isNickNameValid("1234G")); //true
        System.out.println(ValidationUtil.isNickNameValid("123154")); //true
        System.out.println(ValidationUtil.isNickNameValid("123%154")); //false
        System.out.println(ValidationUtil.isNickNameValid("bom&3")); //false
    }

}
