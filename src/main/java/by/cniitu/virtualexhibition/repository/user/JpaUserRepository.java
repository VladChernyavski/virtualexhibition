package by.cniitu.virtualexhibition.repository.user;

import by.cniitu.virtualexhibition.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface JpaUserRepository extends JpaRepository<User, Integer> {

    User getByEmail(String email);

    @Query(value = "INSERT INTO subscription_subscribers VALUES (?1, ?2)", nativeQuery = true)
    @Modifying
    @Transactional
    void subscribe(int userId, int subscriberId);

    @Query(value = "DELETE FROM subscription_subscribers WHERE subscriber_id = ?1 AND user_id = ?2", nativeQuery = true)
    @Modifying
    @Transactional
    void unsubscribe(int userId, int subscriberId);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM subscription_subscribers " +
            "WHERE user_id = ?1 AND subscriber_id = ?2)", nativeQuery = true)
    Boolean isUserSubscribed(int userId, int subscriberId);

    @Query(nativeQuery = true, value = "SELECT * FROM user_table WHERE LOWER(nickname) LIKE LOWER(CONCAT('%', ?1, '%')) ")
    List<User> getUserByNickName(String nickName);

    @Query(nativeQuery = true, value = "SELECT * FROM user_table WHERE LOWER(first_name) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<User> getUserByName(String name);

    @Query(nativeQuery = true, value = "SELECT * FROM user_table WHERE LOWER(last_name) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<User> getUserBySurname(String surname);

    @Query(nativeQuery = true, value = "SELECT * FROM user_table WHERE LOWER(email) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<User> getUserByEmail(String email);
}
