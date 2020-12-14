package by.cniitu.virtualexhibition.config;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@EnableScheduling
public class ScheduledTask {

    // todo change time and directory
    @Scheduled(fixedRate = 600000)
    public void deleteTempXlsFiles(){
//        String directory = "/opt/temp";
        String directoryPC = "C:\\Users\\u108\\Desktop\\EXCEL";
        File[] files = new File(directoryPC).listFiles();

        if (files != null){
            for (File f : files){
                if(f.isFile()){
                    System.out.println(f.getAbsolutePath() + " is deleted: " + f.delete());
                }
            }
        }

    }

}
