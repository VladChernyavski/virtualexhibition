package by.cniitu.virtualexhibition.repository.file;

import by.cniitu.virtualexhibition.entity.file.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface JpaFileRepository extends JpaRepository<File, Integer> {

    @Query("SELECT so.files FROM StandObject so WHERE so.id = ?1")
    List<File> getAllByStandObjectId(int id);

    File getFileById(int id);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM file_stand_object fso " +
            "JOIN user_table ut ON ut.id = ?2 " +
            "JOIN stand s ON ut.id = s.owner_id " +
            "JOIN stand_object so ON s.id = so.stand_id " +
            "WHERE fso.file_id = ?1 AND fso.stand_object_id = so.id)", nativeQuery = true)
    Boolean isFileExists(int fileId, int userId);

    @Modifying
    @Query(value = "DELETE FROM file_stand_object fso WHERE fso.file_id = ?1 ", nativeQuery = true)
    @Transactional
    void delete(int fileId);

    @Query("SELECT f FROM File f WHERE f.user.id = ?1")
    List<File> getFilesByUserId(int userId);
}
