package by.cniitu.virtualexhibition.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserActionTo {

    private String name;
    private String email;
    private String fileName;
    private String fileType;
    private String actionType;
    private String date;

}
