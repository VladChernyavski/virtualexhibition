package by.cniitu.virtualexhibition.entity.file;

import by.cniitu.virtualexhibition.entity.exhibition.ExhibitionObject;
import by.cniitu.virtualexhibition.entity.exhibition.StandObject;
import by.cniitu.virtualexhibition.entity.user.User;
import by.cniitu.virtualexhibition.entity.user.UserAction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "file")
@JsonIgnoreProperties({ "standObjects" })
@Getter
@Setter
@ToString
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "path")
    private String path;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private FileType fileType;

    @ManyToMany
    @JoinTable(name = "file_stand_object",
    joinColumns = @JoinColumn(name = "file_id"),
    inverseJoinColumns = @JoinColumn(name = "stand_object_id"))
    private List<StandObject> standObjects;

    @ManyToMany
    @JoinTable(name = "file_exhibition_object",
    joinColumns = @JoinColumn(name = "file_id"),
    inverseJoinColumns = @JoinColumn(name = "exhib_object_id"))
    private List<ExhibitionObject> exhibitionObjects;

    @OneToMany(mappedBy = "file")
    private List<UserAction> actions;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
