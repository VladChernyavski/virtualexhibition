package by.cniitu.virtualexhibition.service;

import by.cniitu.virtualexhibition.entity.file.File;
import by.cniitu.virtualexhibition.repository.file.JpaFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    @Autowired
    private JpaFileRepository fileRepository;

    public List<File> getFilesByStandObjectId(int id){
        return fileRepository.getAllByStandObjectId(id);
    }

    public File getFile(int id){
        return fileRepository.getFileById(id);
    }

}
