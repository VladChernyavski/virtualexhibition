package by.cniitu.virtualexhibition.web.controller.stand;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDescriptionRequest {

    private Integer standId;
    private String description;

}
