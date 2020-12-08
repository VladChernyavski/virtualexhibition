package by.cniitu.virtualexhibition;

import by.cniitu.virtualexhibition.web.controller.file.FileUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VirtualExhibitionApplication {

    public static void main(String[] args) {

        if(args.length > 0)
            FileUtil.setMachine("cniitu");

        SpringApplication.run(VirtualExhibitionApplication.class, args);
    }

}
