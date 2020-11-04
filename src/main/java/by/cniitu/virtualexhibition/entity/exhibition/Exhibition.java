package by.cniitu.virtualexhibition.entity.exhibition;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "exhibition")
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
