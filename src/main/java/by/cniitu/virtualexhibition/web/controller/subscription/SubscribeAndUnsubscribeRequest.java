package by.cniitu.virtualexhibition.web.controller.subscription;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscribeAndUnsubscribeRequest {

    //пользователь на которого подписываються
    private Integer userId;
    //пользователь который подписываеться
    private Integer subscriberId;

}
