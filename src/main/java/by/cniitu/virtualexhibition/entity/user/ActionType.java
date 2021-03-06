package by.cniitu.virtualexhibition.entity.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "action_type")
@Getter
@Setter
public class ActionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "actionType")
    private List<UserAction> actions;

}
