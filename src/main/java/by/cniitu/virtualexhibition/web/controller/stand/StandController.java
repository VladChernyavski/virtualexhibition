package by.cniitu.virtualexhibition.web.controller.stand;

import by.cniitu.virtualexhibition.entity.exhibition.Stand;
import by.cniitu.virtualexhibition.repository.stand.StandIdNameAndExhibitName;
import by.cniitu.virtualexhibition.service.StandService;
import by.cniitu.virtualexhibition.to.StandAndUserTo;
import by.cniitu.virtualexhibition.to.websocket.messageBody.chat.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.List;

@CrossOrigin("*")
@RestController
public class StandController {

    @Autowired
    private StandService standService;

    @GetMapping("/my_stands/{exhibitId}/{userId}")
    public ResponseEntity<Object> getStandsList(@PathVariable int exhibitId, @PathVariable int userId) {
        List<Object[]> stands = standService.findAllByOwnerIdAndExhibitId(userId, exhibitId);
        if (Objects.isNull(stands)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"There is no user with this id\"}");
        }
        List<StandIdNameAndExhibitName> standIdNameAndExhibitNames = new LinkedList<>();
        for (Object[] stand : stands) {
            standIdNameAndExhibitNames.add(
                    new StandIdNameAndExhibitName((Integer) stand[0], (String) stand[1])
            );
        }
        return ResponseEntity.ok(standIdNameAndExhibitNames);
    }

    @GetMapping("/stands")
    public ResponseEntity<Object> getStandsByName(@RequestParam String name) {
        List<StandAndUserTo> standsByName = standService.getStandsByName(name);
        return ResponseEntity.ok(standsByName);
    }

    @GetMapping("/stands/{standId}/description")
    public ResponseEntity<Object> getDescription(@PathVariable int standId) {
        return ResponseEntity.ok(standService.getDescription(standId));
    }

    @PostMapping("stands/updatedesc")
    public ResponseEntity<Object> updateDescription(@RequestBody UpdateDescriptionRequest request) {
        standService.updateDescription(request.getDescription(), request.getStandId());
        return ResponseEntity.ok("{\"message\": \"Description is updated\"}");
    }

    @PostMapping("/stands/{standId}")
    public ResponseEntity<Object> updateIsSpeakingRoom(@RequestParam Boolean flag, @PathVariable int standId) {
        Stand stand = standService.getStand(standId);

        if (stand == null) {
            return ResponseEntity.badRequest().body("{\"message\": \"No such stand\"}");
        }
        standService.updateIsSpeakingRoom(flag, standId);

        if (!flag) {
            if (Chat.chats.stream().anyMatch(c -> c.getId().equals(stand.getChatId()))) {
                standService.resetChatId(standId);
                Chat.chats.removeIf(c -> c.getId().equals(stand.getChatId()));
                System.out.println("Chat with id " + stand.getChatId() + " was removed");
                System.out.println("SET Chats -> " + Chat.chats);
            }
        }
        if (flag) {
            if (Chat.chats.stream().noneMatch(c -> c.getId().equals(stand.getChatId()))) {
                Chat chat = new Chat();
                String chatId = "stand_chat_" + stand.getId();
                chat.setId(chatId);
                if(chat.getUserIds() == null){
                    chat.setUserIds(new HashSet<>());
                }
                chat.getUserIds().add(stand.getUser().getId());
                standService.insertChatId(chatId, standId);
                Chat.chats.add(chat);
                System.out.println("Chat with id " + chatId + " created");
                System.out.println("SET Chats -> " + Chat.chats);
            }
        }
        return ResponseEntity.ok("{\"message\": \"Flag is changed\"}");
    }

}
