package by.cniitu.virtualexhibition.web.controller.file;

import org.springframework.core.io.InputStreamResource;

import java.io.File;
import java.io.FileInputStream;


public class FileUtil {

//     path to directory on server. take from properties
    private static String pathToObject = "/opt/unityobject/";
    private static String pathToFile = "/***/";

    public static void setPathToObject(String path) {
        FileUtil.pathToObject = path;
    }

    public static void setPathToFile(String path) {
        FileUtil.pathToFile = path;
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
        while (inputStreamResource == null) {
            try {
                Thread.sleep(100);
                file = getFile(pathToFile + fileName);
                if (file != null)
                    inputStreamResource = new InputStreamResource(new FileInputStream(file));
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return new FileAndInputStreamResource(file, inputStreamResource);
    }

    public static FileAndInputStreamResource getFileAndInputStreamResource(String fileName, String os) {
        InputStreamResource inputStreamResource = null;
        File file = null;
        while (inputStreamResource == null) {
            try {
                Thread.sleep(100);
                file = getFile(pathToObject + os + "/" + fileName);
                if (file != null)
                    inputStreamResource = new InputStreamResource(new FileInputStream(file));
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return new FileAndInputStreamResource(file, inputStreamResource);
    }

    // gets file and retries if it's needed <retryCount> times
    private static File getFile(String fileName){
        File file = null;
        while(file == null){
            try{
                Thread.sleep(100);
                file = new File(fileName);
            } catch (Exception ex){
                ex.printStackTrace();
                return null;
            }
        }
        return file;
    }


}
