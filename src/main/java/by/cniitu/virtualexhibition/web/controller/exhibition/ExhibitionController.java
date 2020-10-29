package by.cniitu.virtualexhibition.web.controller.exhibition;

import by.cniitu.virtualexhibition.service.ExhibitionService;
import by.cniitu.virtualexhibition.to.ExhibitionTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExhibitionController {

    @Autowired
    private ExhibitionService exhibitionService;

    @GetMapping(value = "/exhibition/{id}")
    public ResponseEntity<ExhibitionTo> getExhibition(@PathVariable int id){
        return ResponseEntity.ok(exhibitionService.getExhibitionJson(id));
    }

}
