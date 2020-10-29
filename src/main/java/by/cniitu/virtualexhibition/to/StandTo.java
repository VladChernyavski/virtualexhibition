package by.cniitu.virtualexhibition.to;

import by.cniitu.virtualexhibition.entity.exhibition.StandModel;
import by.cniitu.virtualexhibition.entity.user.User;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class StandTo {

    private Integer id;
    private String name;
    private LinkedList<Double> coordinates;
    private LinkedList<Double> rotations;
    private LinkedList<Double> scale;
    private StandModel standModel;
    private User user;
    private List<StandObjectTo> standObjects;


}
