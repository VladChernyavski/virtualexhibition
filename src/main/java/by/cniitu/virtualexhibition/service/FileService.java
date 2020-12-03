package by.cniitu.virtualexhibition.service;

import by.cniitu.virtualexhibition.entity.file.File;
import by.cniitu.virtualexhibition.repository.file.JpaFileRepository;
import by.cniitu.virtualexhibition.web.controller.file.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class FileService {

    @Value("${machine}")
    private String machine;

    private final JpaFileRepository fileRepository;

    public FileService(JpaFileRepository fileRepository){
        this.fileRepository = fileRepository;
    }

    @PostConstruct
    public void setMachine() {
        FileUtil.setMachine(machine);
    }

    public List<File> getFilesByStandObjectId(int id){
        return fileRepository.getAllByStandObjectId(id);
    }

    public File getFile(int id){
        return fileRepository.getFileById(id);
    }

    public boolean isFileExists(int fileId, int userId){
        return fileRepository.isFileExists(fileId, userId);
    }

    public void deleteFile(int fileId){
        fileRepository.delete(fileId);
    }

}
