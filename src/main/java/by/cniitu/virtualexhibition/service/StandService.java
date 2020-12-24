package by.cniitu.virtualexhibition.service;

import by.cniitu.virtualexhibition.entity.exhibition.Stand;
import by.cniitu.virtualexhibition.repository.stand.JpaStandRepository;
import by.cniitu.virtualexhibition.to.StandAndUserTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StandService {

    @Autowired
    private JpaStandRepository standRepository;

    public List<Object[]> findAllByOwnerIdAndExhibitId(int id, int exhibId){
        return standRepository.findAllByOwnerIdAndExhibitId(id, exhibId);
    }

    public List<StandAndUserTo> getStandsByName(String name){
        return standRepository.getStandsByName(name).stream()
                .map(stand -> new StandAndUserTo(stand.getName(), stand.getUser().getEmail()))
                .collect(Collectors.toList());
    }

    public String getDescription(int standId){
        return standRepository.getDescription(standId);
    }

    public void updateDescription(String description, int id){
        standRepository.updateDescription(description, id);
    }

    public void updateIsSpeakingRoom(boolean flag, int standId){
        standRepository.updateIsSpeakingRoom(flag, standId);
    }

    public void resetChatId(int standId){
        standRepository.resetChatId(standId);
    }

    public void insertChatId(String chatId, int standId){
        standRepository.insertChatId(chatId, standId);
    }

    public Stand getStand(int id){
        return standRepository.findById(id).orElse(null);
    }

}
