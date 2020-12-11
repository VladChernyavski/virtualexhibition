package by.cniitu.virtualexhibition.entity.exhibition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "exhibition")
@JsonIgnoreProperties({ "stands", "exhibitionObjects" })
@Getter
@Setter
public class Exhibition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "exhibition")
    private List<Stand> stands;

    @OneToMany(mappedBy = "exhibition")
    private List<ExhibitionObject> exhibitionObjects;

}
