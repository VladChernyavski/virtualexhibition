package by.cniitu.virtualexhibition.repository.action;

import by.cniitu.virtualexhibition.entity.user.UserAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface JpaUserActionRepository extends JpaRepository<UserAction, Integer> {

    @Modifying
    @Query(value = "INSERT INTO user_action (user_id, file_id, action_type_id, action_time) " +
            "VALUES (?1, ?2, ?3, CURRENT_TIMESTAMP)", nativeQuery = true)
    @Transactional
    void saveUserActionByIds(int userId, int fileId, int actionTypeId);

    @Query("SELECT ua FROM UserAction ua WHERE ua.id = ?1")
    UserAction findById(int id);

    @Query(value = "SELECT DISTINCT ua.id, ua.user_id, ua.file_id, ua.action_type_id, ua.action_time FROM user_action ua " +
            "JOIN action_type at ON ua.action_type_id = at.id " +
            "JOIN file f ON ua.file_id = f.id " +
            "JOIN user_table ut ON ut.id = ua.user_id " +
            "JOIN stand s ON ut.id = s.owner_id " +
            "JOIN exhibition e ON s.exhibition_id = e.id " +
            "WHERE e.id = ?1 AND ua.user_id = ?2 ", nativeQuery = true)
//    @Query("SELECT DISTINCT ua FROM UserAction ua " +
//            "JOIN FETCH ActionType at ON ua.actionType.id = at.id " +
//            "JOIN FETCH File f ON ua.file.id = f.id " +
//            "JOIN FETCH User u ON ua.user.id = u.id " +
//            "JOIN FETCH Stand s ON u.id = s.user.id " +
//            "JOIN FETCH Exhibition  e ON s.exhibition.id = e.id " +
//            "WHERE e.id = ?1 AND ua.user.id = ?2 ")
    List<UserAction> getUserActionByExhibAndUserId(int exhibitionId, int userId);

    @Query(value = "SELECT ua.id, ua.user_id, ua.file_id, ua.action_type_id, ua.action_time FROM user_action ua " +
            "JOIN file f ON f.id = ua.file_id " +
            "JOIN file_stand_object fso on f.id = fso.file_id " +
            "JOIN stand_object so on so.id = fso.stand_object_id " +
            "JOIN stand s on so.stand_id = s.id " +
            "WHERE s.id = ?1", nativeQuery = true)
    List<UserAction> getUserActionByStandId(int standId);

    @Query(value = "SELECT s.id FROM stand s WHERE s.owner_id = ?1", nativeQuery = true)
    List<Integer> getStandIdsByUser(int userId);

    @Query(value = "SELECT COUNT(*) FROM user_action WHERE file_id = ?1", nativeQuery = true)
    Integer getActionsByFileId(int fileId);
}
