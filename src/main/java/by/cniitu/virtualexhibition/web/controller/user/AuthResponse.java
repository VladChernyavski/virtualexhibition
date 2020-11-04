package by.cniitu.virtualexhibition.web.controller.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {

    private Integer id;
    private String token;
    private String nickName;
    private String role;

    public AuthResponse(String token){
        this.token = token;
    }

}
