package by.cniitu.virtualexhibition.web.controller.file;

import org.springframework.core.io.InputStreamResource;

import java.io.File;
import java.io.FileInputStream;


public class FileUtil {

    // path to directory on server. take from properties
    private static String path = "/opt/unityobject/";

    public static void setPath(String path) {
        FileUtil.path = path;
    }

    public static String getLittleFileName(String path, int px){
        FileNameAndType fileNameAndType = getFileNameAndType(path);
        return fileNameAndType.name + "_" + px + "." + fileNameAndType.type;
    }

    public static FileNameAndType getFileNameAndType(String path){
        int indexOfPoint = path.lastIndexOf('.');
        return new FileNameAndType(path.substring(0, indexOfPoint), path.substring(indexOfPoint + 1));
    }

    public static boolean exist(String fileName){
        FileAndInputStreamResource fileAndInputStreamResource = getFileAndInputStreamResource(fileName);

        if(fileAndInputStreamResource == null)
            return false;
        return true;
    }

    // gets file with InputStreamResource and retries if it's needed <retryCount> times
    public static FileAndInputStreamResource getFileAndInputStreamResource(String fileName) {
        InputStreamResource inputStreamResource = null;
        File file = null;
        int count = 0;
        while (inputStreamResource == null) {
            try {
                Thread.sleep(100);
                file = getFile(fileName);
                if (file != null)
                    inputStreamResource = new InputStreamResource(new FileInputStream(file));
            } catch (Exception ex) {
                // System.out.println(ex.getMessage());
                count++;
                // if(count > retryCount)
                return null;
            }
        }
        return new FileAndInputStreamResource(file, inputStreamResource);
    }

    // gets file and retries if it's needed <retryCount> times
    private static File getFile(String fileName){
        File file = null;
        int count = 0;
        while(file == null){
            try{
                Thread.sleep(100);
                file = new File(path + fileName);
            } catch (Exception ex){
                ex.printStackTrace();
                count++;
                // if(count > retryCount)
                return null;
            }
        }
        return file;
    }


}
