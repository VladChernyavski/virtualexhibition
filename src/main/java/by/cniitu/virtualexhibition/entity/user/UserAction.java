package by.cniitu.virtualexhibition.entity.user;

import by.cniitu.virtualexhibition.entity.file.File;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user_action")
@Getter
@Setter
public class UserAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "action_type_id")
    private ActionType actionType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JoinColumn(name = "action_time")
    private Timestamp action_time;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private File file;

}
