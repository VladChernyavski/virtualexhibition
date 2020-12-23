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

    @Query(value = "UPDATE bundle SET last_used = now() WHERE path LIKE ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void updateBundleUseTime(String fileName);

    // возвращает id файлов которые не использовались за последний час
    @Query(value = "SELECT id FROM bundle WHERE (now() - last_used) > interval '1 hours'", nativeQuery = true)
    List<Integer> getOldBundles();

    @Query(value = "UPDATE file SET last_used = now() WHERE path LIKE ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void updateFileUseTime(String fileName);

    // возвращает id файлов которые не использовались за последний час
    @Query(value = "SELECT id FROM file WHERE (now() - last_used) > interval '1 hours'", nativeQuery = true)
    List<Integer> getOldFiles();

    // проверяет есть ли хотя бы одна запись в таблице stand_model или object_model по bundleId.
    // возвращает true если есть и false в противном случае
    @Query(value = "SELECT EXISTS(SELECT 1 FROM stand_model WHERE stand_model.bundle_id = ?1 OR " +
            "EXISTS(SELECT 1 FROM object_model WHERE object_model.bundle_id = ?1))", nativeQuery = true)
    Boolean isLinkToBundle(int bundleId);

    // проверяет есть ли хотя бы одна запись в таблице user_action, file_stand_object или
    // file_exhibition_object по fileId.
    // возвращает true если есть и false в противном случае
    @Query(value = "SELECT EXISTS(SELECT 1 FROM user_action WHERE file_id = ?1 OR " +
            "EXISTS(SELECT 1 FROM file_stand_object WHERE file_stand_object.file_id = ?1) OR " +
            "EXISTS(SELECT 1 FROM file_exhibition_object WHERE file_exhibition_object.file_id = ?1))", nativeQuery = true)
    Boolean isLinkToFile(int fileId);

    //TODO test. change return value. (Transactional, Modifying)
    @Query(value = "DELETE FROM file WHERE id = ?1", nativeQuery = true)
    Boolean deleteFileById(int fileId);

    //TODO test. change return value. (Transactional, Modifying)
    @Query(value = "DELETE FROM bundle WHERE id = ?1", nativeQuery = true)
    Boolean deleteBundleById(int bundleId);
}
