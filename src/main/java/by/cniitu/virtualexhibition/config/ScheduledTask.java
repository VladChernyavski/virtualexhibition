package by.cniitu.virtualexhibition.config;

import by.cniitu.virtualexhibition.web.controller.file.FileUtil;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Objects;

@Component
@EnableScheduling
public class ScheduledTask {

    // todo change time
    @Scheduled(fixedRate = 60000)
    public void deleteTempXlsFiles(){
        String directory = FileUtil.getFilePath("file") + "//statistics";
        File file = new File(directory);
        if(file.exists() && file.isDirectory()) {
            for (File f : Objects.requireNonNull(file.listFiles())) {
                if (f.isFile()) {
                    System.out.println(f.getAbsolutePath() + " is deleted: " + f.delete());
                }
            }
        }

    }

}
