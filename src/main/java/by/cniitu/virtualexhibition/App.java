package by.cniitu.virtualexhibition;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class App {

    public static void main(String[] args) {

        System.out.println(getString("a4 9f a9 42 n6 5d 46 23 a2 e1 66 c2 e0 36 bd 66"));

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("testtest"));
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

}
