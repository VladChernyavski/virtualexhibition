package by.cniitu.virtualexhibition.web.controller.stand;

import by.cniitu.virtualexhibition.repository.stand.JpaStandRepository;
import by.cniitu.virtualexhibition.repository.stand.StandIdNameAndExhibitName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.Objects;
import java.util.List;

@CrossOrigin("*")
@RestController
public class StandController {

    @Autowired
    private JpaStandRepository jpaStandRepository;

    @GetMapping( "/my_stands/{exhibitId}/{userId}")
    public ResponseEntity<Object> getStandsList(@PathVariable int exhibitId, @PathVariable int userId){
        List<Object[]> stands = jpaStandRepository.findAllByOwnerIdAndExhibitId(userId, exhibitId);
        if(Objects.isNull(stands)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"There is no user with this id\"}");
        }
        List<StandIdNameAndExhibitName> standIdNameAndExhibitNames = new LinkedList<>();
        for(Object[] stand : stands){
            standIdNameAndExhibitNames.add(
                    new StandIdNameAndExhibitName((Integer)stand[0], (String)stand[1])
            );
        }
        return ResponseEntity.ok(standIdNameAndExhibitNames);
    }

}
