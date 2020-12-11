package by.cniitu.virtualexhibition.repository.stand;

import by.cniitu.virtualexhibition.entity.exhibition.StandObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional(readOnly = true)
public interface JpaStandObjectRepository extends JpaRepository<StandObject, Integer> {

}
