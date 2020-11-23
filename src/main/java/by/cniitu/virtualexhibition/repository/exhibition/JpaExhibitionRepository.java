package by.cniitu.virtualexhibition.repository.exhibition;

import by.cniitu.virtualexhibition.entity.exhibition.Exhibition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface JpaExhibitionRepository extends JpaRepository<Exhibition, Integer> {

    @Query("SELECT e FROM Exhibition e WHERE e.id = ?1")
    Exhibition getExhibition(int id);

}
