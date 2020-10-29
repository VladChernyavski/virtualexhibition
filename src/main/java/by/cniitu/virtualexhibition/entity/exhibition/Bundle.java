package by.cniitu.virtualexhibition.entity.exhibition;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bundle")
@JsonIgnoreProperties({ "standModels", "objectModels" })
@Getter
@Setter
public class Bundle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "path")
    private String path;

    @OneToMany(mappedBy = "bundle")
    private List<StandModel> standModels;

    @OneToMany(mappedBy = "bundle")
    private List<ObjectModel> objectModels;

}
