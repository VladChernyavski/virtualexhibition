package by.cniitu.virtualexhibition.web.controller.file;

import by.cniitu.virtualexhibition.entity.user.User;
import by.cniitu.virtualexhibition.repository.file.JpaFileTypeRepository;
import by.cniitu.virtualexhibition.service.FileService;
import by.cniitu.virtualexhibition.service.UserActionService;
import by.cniitu.virtualexhibition.service.UserService;
import by.cniitu.virtualexhibition.util.ActionTypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(value = "/files")
@CrossOrigin("*")
public class FileController {

    @Autowired
    private FileService fileService;
    @Autowired
    private UserActionService actionService;
    @Autowired
    private JpaFileTypeRepository jpaFileTypeRepository;
    @Autowired
    private UserService userService;

    /**
     * download file when we know fileName and type
     * @param type can be "image" or "file". Nothing else can be uploaded yet
     */
    @GetMapping("/api/download")
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam("fileName") String fileName,
                                                            @RequestParam("type") String type) {
        System.out.println("fileName = " + fileName);
        System.out.println("type = " + type);
        FileAndInputStreamResource fileAndInputStreamResource = FileUtil.getFileAndInputStreamResource(fileName, type);
        return getInputStreamResourceResponseEntity(fileAndInputStreamResource);
    }

    private ResponseEntity<InputStreamResource> getInputStreamResourceResponseEntity(FileAndInputStreamResource fileAndInputStreamResource) {
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

    /**
     * @param userId to be used in statistics
     * TODO use it only when user clicks the file
     */
    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam int fileId, @RequestParam int userId) {
        by.cniitu.virtualexhibition.entity.file.File fileFromDB = fileService.getFile(fileId);

        if(Objects.isNull(fileFromDB)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        FileAndInputStreamResource fileAndInputStreamResource
                = FileUtil.getFileAndInputStreamResource(fileFromDB.getPath(),
                fileFromDB.getFileType().getName().equals("image")? "image": "file");

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

    static private Map<String, String> runtimePlatformMap = new HashMap<>();

    static{
        runtimePlatformMap.put("WindowsPlayer", "windows");
        runtimePlatformMap.put("WindowsEditor", "windows");
    }

    @GetMapping("/download_asset")
    public ResponseEntity<InputStreamResource> downloadAsset(@RequestParam("fileName") String fileName,
                                                            @RequestParam("runtimePlatform") String runtimePlatform) {

        String os = runtimePlatformMap.get(runtimePlatform);
        if(os != null)
            runtimePlatform = os;

        FileAndInputStreamResource fileAndInputStreamResource = FileUtil.getFileAndInputStreamResourceAsset(fileName, runtimePlatform);

        return getInputStreamResourceResponseEntity(fileAndInputStreamResource);
    }

    @GetMapping("/standobject/{id}")
    public List<by.cniitu.virtualexhibition.entity.file.File> getAllFilesByStandObjectId(@PathVariable int id){
        return fileService.getFilesByStandObjectId(id);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam int fileId, @RequestParam int userId){
        if(!fileService.isFileExists(fileId, userId)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"Error. File with id: " + fileId + " not found\"}");
        }
        fileService.deleteFile(fileId);
        //add action to DB
        actionService.save(userId, fileId, ActionTypeUtil.actionType.get("deleted"));
        return ResponseEntity.ok("{\"message\": \"Ok. Delete file with id: " + fileId + " \"}");
    }

    // TODO write an endpoint that says to client the link to download the file from id
    // TODO it multiple

    // TODO add everything to the database
    // TODO the name of a file and time of the last modification have to be two separate columns
    // TODO files can have different types. Bundles (different folder), images, ...

    // TODO I will do it

    /**
     * @param type can be "image", "video", "file" or "bundle". Nothing else can be uploaded yet.
     *             runtimePlatform can be only with "bundle"
     */
    @PostMapping("/api/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file,
                                         @RequestParam("lastModified") String lastModified,
                                         @RequestParam("type") String type,
                                         @RequestParam("userId") Integer userId,
                                         @RequestParam("runtimePlatform") String runtimePlatform) throws Exception{
        String originalFilename = file.getOriginalFilename();
        System.out.println("originalFilename = " + originalFilename);
        User user = userService.get(userId);
        if(user == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"no such user\"}");
        if(originalFilename == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"the original file name is null\"}");
        FileNameAndType fileNameAndType = FileUtil.getFileNameAndType(originalFilename);
        String dirName = "user_" + userId + "/";
        String filePath = FileUtil.getFilePath(type);
        if(filePath == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"type error\"}");
        String dirPath = filePath + dirName;
        File dir = new File(dirPath);
        if(!dir.exists()){
            System.out.println(dir.mkdir());
        }
        String newFilename = dirName + fileNameAndType.name + "~~~" + lastModified + "." + fileNameAndType.type;
        String path = filePath + newFilename;
        System.out.println("path = " + path);
        File new_file = new File(path);
        if (!new_file.exists() && new_file.createNewFile()) {
            file.transferTo(new_file);
        }
        System.out.println("saved");
        by.cniitu.virtualexhibition.entity.file.File fileEntity = new by.cniitu.virtualexhibition.entity.file.File();
        fileEntity.setFileType(jpaFileTypeRepository.getOne(FileUtil.fileTypeToFileTypeId.get(type)));
        fileEntity.setPath(newFilename);
        fileEntity.setUser(user);
        return ResponseEntity.ok("{\"savedFileId\": " + fileService.save(fileEntity).getId() + "}");
    }

}
