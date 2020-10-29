package by.cniitu.virtualexhibition.repository.user;

import by.cniitu.virtualexhibition.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

}
