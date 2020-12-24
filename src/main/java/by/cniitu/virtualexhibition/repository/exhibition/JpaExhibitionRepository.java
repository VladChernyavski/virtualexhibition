package by.cniitu.virtualexhibition.repository.exhibition;

import by.cniitu.virtualexhibition.entity.exhibition.Exhibition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface JpaExhibitionRepository extends JpaRepository<Exhibition, Integer> {

    @Query("SELECT e FROM Exhibition e WHERE e.id = ?1")
    Exhibition getExhibition(int id);

    @Query(value = "SELECT * FROM exhibition WHERE LOWER(name) LIKE LOWER(CONCAT('%', ?1, '%'))", nativeQuery = true)
    List<Exhibition> getExhibitionsByName(String name);

    @Query(value = "UPDATE stand SET chat_id = ?1 WHERE id = ?2", nativeQuery = true)
    @Transactional
    @Modifying
    void insertChatIdToStand(String chatId, int standId);

}
