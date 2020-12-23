package by.cniitu.virtualexhibition.repository.stand;

import by.cniitu.virtualexhibition.entity.exhibition.Stand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface JpaStandRepository extends JpaRepository<Stand, Integer> {

    // @Query("SELECT NEW by.cniitu.virtualexhibition.repository.stand.stand_id_name_and_exhibit_name(so.id, so.name, so.exhibition.name) FROM Stand so WHERE so.user.id = ?1")
    @Query("SELECT so.id, so.name FROM Stand so WHERE so.user.id = ?1 AND so.exhibition.id = ?2")
    // List<stand_id_name_and_exhibit_name> getAllByOwnerId(int id);
    List<Object[]> findAllByOwnerIdAndExhibitId(int id, int exhibitId);

    @Query(value = "SELECT new Stand(s.name, s.user) FROM Stand s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<Stand> getStandsByName(String name);

    @Query(value = "SELECT s.description FROM stand s WHERE s.id = ?1", nativeQuery = true)
    String getDescription(int standId);

    @Query(value = "UPDATE stand SET description = ?1 WHERE id = ?2", nativeQuery = true)
    @Transactional
    @Modifying
    void updateDescription(String description, int standId);

}
