package by.cniitu.virtualexhibition.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_roles")
@Data
@JsonIgnoreProperties({ "authority" })
public class UserRole implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "role")
    private String name;

    @Override
    public String getAuthority() {
        return "ROLE_" + getName();
    }
}
