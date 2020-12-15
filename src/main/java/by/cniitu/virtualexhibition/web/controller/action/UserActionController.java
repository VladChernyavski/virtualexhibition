package by.cniitu.virtualexhibition.web.controller.action;

import by.cniitu.virtualexhibition.entity.user.User;
import by.cniitu.virtualexhibition.service.UserActionService;
import by.cniitu.virtualexhibition.service.UserService;
import by.cniitu.virtualexhibition.to.UserActionTo;
import by.cniitu.virtualexhibition.util.ActionTypeUtil;
import by.cniitu.virtualexhibition.web.controller.file.FileAndInputStreamResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

import static by.cniitu.virtualexhibition.web.controller.file.FileUtil.getFileAndInputStreamResource;

@RestController
@RequestMapping(value = "/useraction")
public class UserActionController {

    @Autowired
    private UserActionService userActionService;

    @Autowired
    private UserService userService;

    @CrossOrigin("*")
    @PostMapping
    public ResponseEntity<String> saveUserAction(@RequestBody ActionRequest actionRequest) {
        int userId = actionRequest.getUserId();
        int fileId = actionRequest.getFileId();
        String actionType = actionRequest.getActionType();
//        !!!! save action to DB
        userActionService.save(userId, fileId, ActionTypeUtil.actionType.get(actionType));
        return ResponseEntity.ok("Action saved");
    }

    //TODO test controller
    @CrossOrigin("*")
    @GetMapping(value = "/actions")
    public List<UserActionTo> getUserActionsByExhibitionAndUserId(@RequestParam int exhibitionId, int userId) {
        return userActionService.getActionsByUserId(exhibitionId, userId);
    }

    //TODO test controller
    @CrossOrigin("*")
    @GetMapping(value = "/stand/{standId}/actions")
    public List<UserActionTo> getUserActionsByStandId(@PathVariable int standId) {
        return userActionService.getActionsByStandId(standId);
    }

    @GetMapping("/statistics/{id}")
    public ResponseEntity<Object> getStatistics(@PathVariable int id) {
        User user = userService.get(id);
        if (user == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"No such user\"}");
        }
        if(!user.getRole().getName().equalsIgnoreCase("role_vendor")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"User must have to role vendor\"}");
        }

        FileAndInputStreamResource fileAndInputStreamResource = getFileAndInputStreamResource(userActionService.getStatistics(user));
        if (fileAndInputStreamResource == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"fileAndInputStreamResource == null\"}");
        }

        File file = fileAndInputStreamResource.getFile();
        InputStreamResource resource = fileAndInputStreamResource.getInputStreamResource();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=statistics.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.length())
                .body(resource);
    }

    @GetMapping("/fileactions/{id}")
    public ResponseEntity<Object> getFileAction(@PathVariable int id){
        User user = userService.get(id);
        if (user == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"No such user\"}");
        }
        if(!user.getRole().getName().equalsIgnoreCase("role_vendor")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"User must have to role vendor\"}");
        }

        FileAndInputStreamResource fileAndInputStreamResource = getFileAndInputStreamResource(userActionService.getFileAction(user.getId()));
        if (fileAndInputStreamResource == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"fileAndInputStreamResource == null\"}");
        }

        File file = fileAndInputStreamResource.getFile();
        InputStreamResource resource = fileAndInputStreamResource.getInputStreamResource();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=actionswithfile.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.length())
                .body(resource);
    }

}
