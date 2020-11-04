package by.cniitu.virtualexhibition.web.controller.action;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionRequest {

    private Integer userId;
    private String actionType;
    private Integer fileId;

}
