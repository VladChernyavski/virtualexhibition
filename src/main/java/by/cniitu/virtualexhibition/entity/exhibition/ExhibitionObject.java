package by.cniitu.virtualexhibition.entity.exhibition;

import by.cniitu.virtualexhibition.entity.file.File;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "exhibition_object")
@Getter
@Setter
public class ExhibitionObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "coordinate_x")
    private Double coordinate_x;

    @Column(name = "coordinate_y")
    private Double coordinate_y;

    @Column(name = "coordinate_z")
    private Double coordinate_z;

    @Column(name = "rotation_x")
    private Double rotation_x;

    @Column(name = "rotation_y")
    private Double rotation_y;

    @Column(name = "rotation_z")
    private Double rotation_z;

    @Column(name = "scale_x")
    private Double scale_x;

    @Column(name = "scale_y")
    private Double scale_y;

    @Column(name = "scale_z")
    private Double scale_z;

    @ManyToOne
    @JoinColumn(name = "exhibition_id")
    private Exhibition exhibition;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private ObjectModel objectModel;

    @ManyToMany(mappedBy = "exhibitionObjects")
    private List<File> files;
}
