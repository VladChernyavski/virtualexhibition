package by.cniitu.virtualexhibition.web.controller.file;

import org.springframework.core.io.InputStreamResource;

import java.io.File;
import java.io.FileInputStream;

public class FileUtil {

    private static String pathToObject;
    private static String pathToFile;
    private static String pathToImage;
    private static String machine = null;

    public static void setMachine(String machine) throws IllegalStateException {

        if(FileUtil.machine != null)
            return;

        if (machine.equals("cniitu")) {
            pathToObject = "/opt/unity_objects/";
            pathToFile = "/opt/unity_files/";
            pathToImage = "/opt/unity_images/";
        } else if (machine.equals("bam")){
            pathToObject = "C:/Users/u108/Desktop/theExhibitions/files/unity_objects/";
            pathToFile = "C:/Users/u108/Desktop/theExhibitions/files/unity_files/";
            pathToImage = "C:/Users/u108/Desktop/theExhibitions/files/unity_images/";
        } else if (machine.equals("cvg")){
            // TODO implement
            pathToObject = null;
            pathToFile = null;
            pathToImage = null;
        } else {
            throw new IllegalStateException("meaning of machine name is not implemented!");
        }

        FileUtil.machine = machine;

    }

    /**
     * @param type can be "image", "file" or "bundle". Nothing else can be uploaded yet
     */
    public static String getFilePath(String type){
        if(type.equals("image"))
            return pathToImage;
        if(type.equals("file"))
            return pathToFile;
        if(type.equals("bundle"))
            return pathToImage;
        return null;
    }

    public static FileNameAndType getFileNameAndType(String path){
        int indexOfPoint = path.lastIndexOf('.');
        return new FileNameAndType(path.substring(0, indexOfPoint), path.substring(indexOfPoint + 1));
    }


    /**
     * TODO use it
     * @param type can be "image" or "file". Nothing else can be uploaded yet
     */
    public static boolean exist(String fileName, String type){
        FileAndInputStreamResource fileAndInputStreamResource = getFileAndInputStreamResource(fileName, type);

        if(fileAndInputStreamResource == null)
            return false;
        return true;
    }

    /**
     * gets file with InputStreamResource and retries if it's needed <retryCount> times
     * @param type can be "image" or "file". Nothing else can be uploaded yet
     */
    public static FileAndInputStreamResource getFileAndInputStreamResource(String fileName, String type) {
        InputStreamResource inputStreamResource = null;
        File file = null;
        while (inputStreamResource == null) {
            try {
                Thread.sleep(100);
                file = getFile(getFilePath(type) + fileName);
                if (file != null)
                    inputStreamResource = new InputStreamResource(new FileInputStream(file));
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return new FileAndInputStreamResource(file, inputStreamResource);
    }

    public static FileAndInputStreamResource getFileAndInputStreamResourceAsset(String fileName, String os) {
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
