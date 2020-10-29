package by.cniitu.virtualexhibition.entity.exhibition;

import by.cniitu.virtualexhibition.entity.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "stand")
@JsonIgnoreProperties({ "exhibition" })
@Getter
@Setter
public class Stand {

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
    @JoinColumn(name = "model_id")
    private StandModel standModel;

    @ManyToOne
    @JoinColumn(name = "exhibition_id")
    private Exhibition exhibition;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User user;

    @OneToMany(mappedBy = "stand")
    private List<StandObject> standObjects;

}
