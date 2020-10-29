package by.cniitu.virtualexhibition.repository.user;

import by.cniitu.virtualexhibition.entity.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<UserRole, Integer> {

    UserRole findByName(String role);

}
