package by.cniitu.virtualexhibition.web.controller.file;

import by.cniitu.virtualexhibition.service.FileService;
import by.cniitu.virtualexhibition.service.UserActionService;
import by.cniitu.virtualexhibition.util.ActionTypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/files")
public class FileController {

    @Autowired
    private FileService fileService;
    @Autowired
    private UserActionService actionService;

    // TODO DELETE
    @GetMapping("/api/download_old")
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam("file") String fileName) {
        FileAndInputStreamResource fileAndInputStreamResource = FileUtil.getFileAndInputStreamResource(fileName);

        if (fileAndInputStreamResource == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        File file = fileAndInputStreamResource.getFile();
        InputStreamResource resource = fileAndInputStreamResource.getInputStreamResource();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.length()) //
                .body(resource);
    }

    @CrossOrigin("*")
    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam int fileId, @RequestParam int userId) {
        by.cniitu.virtualexhibition.entity.file.File fileFromDB = fileService.getFile(fileId);

        if(Objects.isNull(fileFromDB)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        FileAndInputStreamResource fileAndInputStreamResource = FileUtil.getFileAndInputStreamResource(fileFromDB.getPath());

        if (fileAndInputStreamResource == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        File file = fileAndInputStreamResource.getFile();
        InputStreamResource resource = fileAndInputStreamResource.getInputStreamResource();

        //save action to DB
        actionService.save(userId, fileFromDB.getId(), ActionTypeUtil.actionType.get("downloaded"));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.length()) //
                .body(resource);
    }

    @CrossOrigin("*")
    @GetMapping("/download_asset")
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam("file") String fileName,
                                                            @RequestParam("os") String os) {
        FileAndInputStreamResource fileAndInputStreamResource = FileUtil.getFileAndInputStreamResource(fileName, os);

        if (fileAndInputStreamResource == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        File file = fileAndInputStreamResource.getFile();
        InputStreamResource resource = fileAndInputStreamResource.getInputStreamResource();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.length()) //
                .body(resource);
    }

    @CrossOrigin("*")
    @GetMapping("/standobject/{id}")
    public List<by.cniitu.virtualexhibition.entity.file.File> getAllFilesByStandObjectId(@PathVariable int id){
        return fileService.getFilesByStandObjectId(id);
    }

    @CrossOrigin("*")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam int fileId, @RequestParam int userId){
        if(!fileService.isFileExists(fileId, userId)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File with id " + fileId +" not found ");
        }
        fileService.deleteFile(fileId);
        //add action to DB
        actionService.save(userId, fileId, ActionTypeUtil.actionType.get("deleted"));
        return ResponseEntity.ok("Delete file with id: " + fileId);
    }

}
