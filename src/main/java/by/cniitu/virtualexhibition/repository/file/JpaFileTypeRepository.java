package by.cniitu.virtualexhibition.repository.file;

import by.cniitu.virtualexhibition.entity.file.FileType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional(readOnly = true)
public interface JpaFileTypeRepository extends JpaRepository<FileType, Integer> {

}
