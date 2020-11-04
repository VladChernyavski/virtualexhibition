package by.cniitu.virtualexhibition.service;

import by.cniitu.virtualexhibition.entity.exhibition.Exhibition;
import by.cniitu.virtualexhibition.repository.exhibition.JpaExhibitionRepository;
import by.cniitu.virtualexhibition.to.ExhibitionTo;
import by.cniitu.virtualexhibition.util.ExhibitionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExhibitionService {

    @Autowired
    private JpaExhibitionRepository exhibitionRepository;

    public ExhibitionTo getExhibitionJson(int id) {
        Exhibition exhibition = exhibitionRepository.getExhibition(id);

        ExhibitionTo exhibitionTo = new ExhibitionTo();
        exhibitionTo.setId(exhibition.getId());
        exhibitionTo.setName(exhibition.getName());
        exhibitionTo.setStands((ExhibitionUtil.getStandTo(exhibition.getStands())));
        exhibitionTo.setExhibitionObjects(ExhibitionUtil.getExhibitionObjectTos(exhibition.getExhibitionObjects()));

        return exhibitionTo;
    }

}
