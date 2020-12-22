package by.cniitu.virtualexhibition.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserTo {

    private Integer id;
    private String firstName;
    private String lastName;
    private String nickName;
    private String email;
    private String role;


}
