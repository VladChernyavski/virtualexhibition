package by.cniitu.virtualexhibition.repository.action;

import by.cniitu.virtualexhibition.entity.user.UserAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface JpaUserActionRepository extends JpaRepository<UserAction, Integer> {

    @Modifying
    @Query(value = "INSERT INTO user_action (user_id, file_id, action_type_id) " +
            "VALUES (?1, ?2, ?3)", nativeQuery = true)
    @Transactional
    void saveUserActionByIds(int userId, int fileId, int actionTypeId);

    @Query("SELECT ua FROM UserAction ua WHERE ua.id = ?1")
    UserAction findById(int id);

    @Query("SELECT u.userActions FROM User u WHERE u.id = ?1")
    List<UserAction> getAllByUserId(int id);
}
