package by.cniitu.virtualexhibition.service;

import by.cniitu.virtualexhibition.entity.exhibition.Exhibition;
import by.cniitu.virtualexhibition.repository.exhibition.JpaExhibitionRepository;
import by.cniitu.virtualexhibition.to.ExhibitionTo;
import by.cniitu.virtualexhibition.to.StandTo;
import by.cniitu.virtualexhibition.to.websocket.messageBody.chat.Chat;
import by.cniitu.virtualexhibition.util.ExhibitionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
public class ExhibitionService {

    @Autowired
    private JpaExhibitionRepository exhibitionRepository;

    public ExhibitionTo getExhibitionJson(int id) {
        Exhibition exhibition = exhibitionRepository.getExhibition(id);
        if(Objects.isNull(exhibition)){
            return null;
        }

        ExhibitionTo exhibitionTo = new ExhibitionTo();
        exhibitionTo.setId(exhibition.getId());
        exhibitionTo.setName(exhibition.getName());

        List<StandTo> standTos = insertChatId(ExhibitionUtil.getStandTo(exhibition.getStands()));
        exhibitionTo.setStands(standTos);

        exhibitionTo.setExhibitionObjects(ExhibitionUtil.getExhibitionObjectTos(exhibition.getExhibitionObjects()));



        return exhibitionTo;
    }

    public List<Exhibition> getExhibitions(){
        return exhibitionRepository.findAll();
    }

    public List<Exhibition> getExhibitionsByName(String name){
        return exhibitionRepository.getExhibitionsByName(name);
    }

    private List<StandTo> insertChatId(List<StandTo> standTos){
        for (StandTo stand : standTos){

            if(stand.getIsSpeakingRoom()){
                if(Chat.chats.stream().noneMatch(c -> c.getId().equals(stand.getChatId()))){
                    Chat chat = new Chat();
                    String chatId = "stand_chat_" + stand.getId();
                    chat.setId(chatId);
                    stand.setChatId(chatId);
                    if(chat.getUserIds() == null){
                        chat.setUserIds(new HashSet<>());
                    }
                    chat.getUserIds().add(stand.getUser().getId());
                    System.out.println("Created chat with id: " + chat.getId());
                    Chat.chats.add(chat);
                    exhibitionRepository.insertChatIdToStand(chatId, stand.getId());
                    System.out.println("SET Chats -> " + Chat.chats);
                }
            }

        }
        return standTos;
    }

}
