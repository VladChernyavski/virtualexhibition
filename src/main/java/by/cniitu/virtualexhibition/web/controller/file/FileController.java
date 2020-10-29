package by.cniitu.virtualexhibition.web.controller.file;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class FileController {

    @GetMapping("/api/download/{fileName}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String fileName) {
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

}
