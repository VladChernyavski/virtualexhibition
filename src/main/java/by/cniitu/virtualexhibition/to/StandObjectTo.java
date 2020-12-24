package by.cniitu.virtualexhibition.to;

import by.cniitu.virtualexhibition.entity.exhibition.ObjectModel;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class StandObjectTo {

    private Integer id;
    private String name;
    private LinkedList<Double> coordinates;
    private LinkedList<Double> rotations;
    private LinkedList<Double> scale;
    private ObjectModel objectModel;
    private String texture;
    private Boolean image;
    private String video;
    private Boolean hasVideo;
    private Boolean hasFiles;
    private List<Integer> fileIds;

}
