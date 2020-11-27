package by.cniitu.virtualexhibition.to.websocket.messageBody;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public abstract class MessageBody  {

    @NotNull
    private String messageDesc;

}
