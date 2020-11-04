package by.cniitu.virtualexhibition.to;

import by.cniitu.virtualexhibition.entity.exhibition.ObjectModel;
import lombok.Data;

import java.util.LinkedList;

@Data
public class ExhibitionObjectTo {

    private Integer id;
    private String name;
    private LinkedList<Double> coordinates;
    private LinkedList<Double> rotations;
    private LinkedList<Double> scale;
    private ObjectModel objectModel;

}
