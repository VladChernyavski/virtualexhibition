package by.cniitu.virtualexhibition.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserActionTo {

    private Integer userActionId;
    private String fileType;
    private String userActionType;

}
