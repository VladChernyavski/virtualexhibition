package by.cniitu.virtualexhibition.web.controller.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.io.InputStreamResource;

import java.io.File;

// Just to keep File together with InputStreamResource
@AllArgsConstructor
@Getter
public class FileAndInputStreamResource {

    final File file;

    final InputStreamResource inputStreamResource;

}
