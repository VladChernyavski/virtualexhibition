package by.cniitu.virtualexhibition.web.controller.file;

import by.cniitu.virtualexhibition.entity.file.FileType;
import org.springframework.core.io.InputStreamResource;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class FileUtil {

    private static String machine = null;

    private static String pathToObject;
    private static String pathToFile;
    private static String pathToImage;
    private static String pathToVideo;
    private static String externalIp;

    public static Map<String, Integer> fileTypeToFileTypeId = new HashMap<>();

    public static void setFileTypes(List<FileType> fileTypes) {
        for (FileType fileType : fileTypes) {
            fileTypeToFileTypeId.put(fileType.getName(), fileType.getId());
        }
    }

    public static void setMachine(String machine) throws IllegalStateException {

        if (FileUtil.machine != null)
            return;

        if (machine.equals("cniitu")) {
            pathToObject = "/opt/unity_objects/";
            pathToFile = "/opt/unity_files/";
            pathToImage = "/opt/unity_images/";
            pathToVideo = "/opt/unity_video/";
            externalIp = "10.65.65.20";
        } else if (machine.equals("bam")) {
            pathToObject = "C:/Users/u108/Desktop/theExhibitions/files/unity_objects/";
            pathToFile = "C:/Users/u108/Desktop/theExhibitions/files/unity_files/";
            pathToImage = "C:/Users/u108/Desktop/theExhibitions/files/unity_images/";
            pathToVideo = "C:/Users/u108/Desktop/theExhibitions/files/unity_video/";
            externalIp = "192.168.0.64";
        } else if (machine.equals("cvg")) {
            pathToObject = "C:\\Users\\u108\\Desktop\\EXHIBITION\\files\\unity_objects\\";
            pathToFile = "C:\\Users\\u108\\Desktop\\EXHIBITION\\files\\unity_files\\";
            pathToImage = "C:\\Users\\u108\\Desktop\\EXHIBITION\\files\\unity_images\\";
            pathToVideo = "C:\\Users\\u108\\Desktop\\EXHIBITION\\files\\unity_video\\";
            externalIp = null;
        } else {
            throw new IllegalStateException("meaning of machine name is not implemented!");
        }

        FileUtil.machine = machine;

    }

    public static String getExternalIp() {
        return externalIp;
    }

    /**
     * @param type can be "image", "file", "video" or "bundle". Nothing else can be uploaded yet
     */
    public static String getFilePath(String type) {
        if (type.equals("image"))
            return pathToImage;
        if (type.equals("file"))
            return pathToFile;
        if (type.equals("bundle"))
            return pathToObject;
        if (type.equals("video"))
            return pathToVideo;
        return null;
    }

    public static FileNameAndType getFileNameAndType(String path) {
        int indexOfPoint = path.lastIndexOf('.');
        return new FileNameAndType(path.substring(0, indexOfPoint), path.substring(indexOfPoint + 1));
    }

    /**
     * gets file with InputStreamResource and retries if it's needed <retryCount> times
     *
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

    public static FileAndInputStreamResource getFileAndInputStreamResource(File file) {
        InputStreamResource inputStreamResource = null;
        while (inputStreamResource == null) {
            try {
                Thread.sleep(100);
                if (file != null)
                    inputStreamResource = new InputStreamResource(new FileInputStream(file));
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return new FileAndInputStreamResource(file, inputStreamResource);
    }

    public static String getReadableFileSize(long bytes){
        int unit = 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = "kMGTPE".charAt(exp-1) + "";
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    // gets file and retries if it's needed <retryCount> times
    private static File getFile(String fileName) {
        File file = null;
        while (file == null) {
            try {
                Thread.sleep(100);
                file = new File(fileName);
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return file;
    }


}
