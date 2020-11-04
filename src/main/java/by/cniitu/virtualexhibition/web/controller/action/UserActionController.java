package by.cniitu.virtualexhibition.web.controller.action;

import by.cniitu.virtualexhibition.service.UserActionService;
import by.cniitu.virtualexhibition.to.UserActionTo;
import by.cniitu.virtualexhibition.util.ActionTypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserActionController {

    @Autowired
    private UserActionService userActionService;

    @PostMapping(value = "/useraction")
    public ResponseEntity<String> saveUserAction(@RequestBody ActionRequest actionRequest){
        int userId = actionRequest.getUserId();
        int fileId = actionRequest.getFileId();
        String actionType = actionRequest.getActionType();
//        !!!!
        userActionService.save(userId,fileId, ActionTypeUtil.actionType.get(actionType));
        return ResponseEntity.ok("Action saved");
    }

    @GetMapping(value = "/user/{id}/actions")
    public List<UserActionTo> getUserActions(@PathVariable int id){
        return userActionService.getActionsByUserId(id);
    }

}
