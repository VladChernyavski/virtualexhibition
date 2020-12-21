package by.cniitu.virtualexhibition.service;

import by.cniitu.virtualexhibition.web.controller.file.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Objects;

@Component
@EnableScheduling
public class ScheduledTaskService {

    @Autowired
    private FileService fileService;

    // todo change time
    @Scheduled(fixedRate = 60000)
    public void deleteTempFiles(){
        String directory = FileUtil.getFilePath("file") + "//temp";
        File file = new File(directory);

        deleteFiles(file);
    }

    private void deleteFiles(File file){
        if(file.exists() && file.isDirectory()){
            for (File f : Objects.requireNonNull(file.listFiles())){
                if(f.isFile()){
                    System.out.println(f.getAbsolutePath() + " is deleted: " + f.delete());
                }
                if (f.isDirectory()){
                    deleteFiles(f);
                }
            }
        }
    }

    @Scheduled(fixedRate = 30000)
    public void showOldFiles(){
        fileService.deleteOldFiles();
    }


}
