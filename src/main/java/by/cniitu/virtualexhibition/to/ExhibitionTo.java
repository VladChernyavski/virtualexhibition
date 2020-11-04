package by.cniitu.virtualexhibition.to;

import lombok.Data;

import java.util.List;

@Data
public class ExhibitionTo {

    private Integer id;
    private String name;
    private List<StandTo> stands;
    private List<ExhibitionObjectTo>  exhibitionObjects;

}
