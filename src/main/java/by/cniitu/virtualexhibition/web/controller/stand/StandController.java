package by.cniitu.virtualexhibition.web.controller.stand;

import by.cniitu.virtualexhibition.entity.exhibition.Stand;
import by.cniitu.virtualexhibition.repository.stand.JpaStandRepository;
import by.cniitu.virtualexhibition.repository.stand.StandIdNameAndExhibitName;
import by.cniitu.virtualexhibition.service.StandService;
import by.cniitu.virtualexhibition.to.StandAndUserTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.Objects;
import java.util.List;

@CrossOrigin("*")
@RestController
public class StandController {

    @Autowired
    private StandService standService;

    @GetMapping( "/my_stands/{exhibitId}/{userId}")
    public ResponseEntity<Object> getStandsList(@PathVariable int exhibitId, @PathVariable int userId){
        List<Object[]> stands = standService.findAllByOwnerIdAndExhibitId(userId, exhibitId);
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

    @GetMapping("/stands")
    public ResponseEntity<Object> getStandsByName(@RequestParam String name){
        List<StandAndUserTo> standsByName = standService.getStandsByName(name);
        return ResponseEntity.ok(standsByName);
    }

}
