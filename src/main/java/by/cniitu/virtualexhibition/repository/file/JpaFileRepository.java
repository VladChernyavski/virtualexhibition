package by.cniitu.virtualexhibition.repository.file;

import by.cniitu.virtualexhibition.entity.file.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaFileRepository extends JpaRepository<File, Integer> {

    @Query("SELECT so.files FROM StandObject so WHERE so.id = ?1")
    List<File> getAllByStandObjectId(int id);

    File getFileById(int id);
}
