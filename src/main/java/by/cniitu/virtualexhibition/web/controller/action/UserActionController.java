package by.cniitu.virtualexhibition.web.controller.action;

import by.cniitu.virtualexhibition.service.UserActionService;
import by.cniitu.virtualexhibition.to.UserActionTo;
import by.cniitu.virtualexhibition.util.ActionTypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/useraction")
public class UserActionController {

    @Autowired
    private UserActionService userActionService;

    @CrossOrigin("*")
    @PostMapping
    public ResponseEntity<String> saveUserAction(@RequestBody ActionRequest actionRequest){
        int userId = actionRequest.getUserId();
        int fileId = actionRequest.getFileId();
        String actionType = actionRequest.getActionType();
//        !!!! save action to DB
        userActionService.save(userId,fileId, ActionTypeUtil.actionType.get(actionType));
        return ResponseEntity.ok("Action saved");
    }

    //TODO test controller
    @CrossOrigin("*")
    @GetMapping(value = "/actions")
    public List<UserActionTo> getUserActionsByExhibitionAndUserId(@RequestParam int exhibitionId, int userId){
        return userActionService.getActionsByUserId(exhibitionId, userId);
    }

    //TODO test controller
    @CrossOrigin("*")
    @GetMapping(value = "/stand/{standId}/actions")
    public List<UserActionTo> getUserActionsByStandId(@PathVariable int standId){
        return userActionService.getActionsByStandId(standId);
    }

}
