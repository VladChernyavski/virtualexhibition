package by.cniitu.virtualexhibition.web.controller.file;

import by.cniitu.virtualexhibition.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/api/download")
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

    @GetMapping("/api/download/{id}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable int id) {
        by.cniitu.virtualexhibition.entity.file.File fileFromDB = fileService.getFile(id);

        FileAndInputStreamResource fileAndInputStreamResource = FileUtil.getFileAndInputStreamResource(fileFromDB.getPath());

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

    @GetMapping("/api/download_asset")
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


    @GetMapping("/standobject/{id}/files")
    public List<by.cniitu.virtualexhibition.entity.file.File> getAllFiles(@PathVariable int id){
        return fileService.getFilesByStandObjectId(id);
    }

}
