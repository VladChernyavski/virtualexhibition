package by.cniitu.virtualexhibition.web.controller.stand;

import by.cniitu.virtualexhibition.entity.exhibition.StandObject;
import by.cniitu.virtualexhibition.entity.file.File;
import by.cniitu.virtualexhibition.repository.file.JpaFileRepository;
import by.cniitu.virtualexhibition.repository.stand.JpaStandObjectRepository;
import by.cniitu.virtualexhibition.repository.stand.JpaStandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
public class StandObjectController {

    @Autowired
    private JpaFileRepository jpaFileRepository;
    @Autowired
    private JpaStandObjectRepository jpaStandObjectRepository;

    @PostMapping( "/texture/{standObjectId}/{textureId}")
    public ResponseEntity<Object> uploadAndSetTexture(@PathVariable int standObjectId, @PathVariable int textureId){
        File file = jpaFileRepository.getFileById(textureId);
        StandObject standObject = jpaStandObjectRepository.getOne(standObjectId);
        standObject.setTexture(file);
        jpaStandObjectRepository.save(standObject);
        return ResponseEntity.ok("{\"message\": \"fine\"}");
    }

}
