package by.cniitu.virtualexhibition.web.controller.exhibition;

import by.cniitu.virtualexhibition.entity.exhibition.Exhibition;
import by.cniitu.virtualexhibition.service.ExhibitionService;
import by.cniitu.virtualexhibition.to.ExhibitionTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class ExhibitionController {

    @Autowired
    private ExhibitionService exhibitionService;

    @CrossOrigin("*")
    @GetMapping(value = "/exhibition/{id}")
    public ResponseEntity<Object> getExhibition(@PathVariable int id){
        ExhibitionTo exhibition = exhibitionService.getExhibitionJson(id);
        if(Objects.isNull(exhibition)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"Error. No such exhibition\"}");
        }
        return ResponseEntity.ok(exhibition);
    }

    @CrossOrigin("*")
    @GetMapping(value = "/exhibitions")
    public List<Exhibition> getListExhibition(){
        return exhibitionService.getExhibitions();
    }

    @GetMapping(value = "/exhibitions/name")
    public ResponseEntity<Object> getExhibitionsByName(@RequestParam String name){
        List<Exhibition> exhibitionsByName = exhibitionService.getExhibitionsByName(name);
        return ResponseEntity.ok(exhibitionsByName);
    }

}
