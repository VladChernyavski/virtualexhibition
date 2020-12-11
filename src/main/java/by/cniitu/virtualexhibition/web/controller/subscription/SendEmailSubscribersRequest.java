package by.cniitu.virtualexhibition.web.controller.subscription;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendEmailSubscribersRequest {

    //id пользователя который отправляет email
    private Integer userId;
    private String subject;
    private String message;

}
