package by.cniitu.virtualexhibition.service;

import by.cniitu.virtualexhibition.entity.user.User;
import by.cniitu.virtualexhibition.entity.user.UserAction;
import by.cniitu.virtualexhibition.repository.action.JpaUserActionRepository;
import by.cniitu.virtualexhibition.repository.file.JpaFileRepository;
import by.cniitu.virtualexhibition.to.FileActionTo;
import by.cniitu.virtualexhibition.to.UserActionTo;
import by.cniitu.virtualexhibition.util.FileActionUtil;
import by.cniitu.virtualexhibition.util.PdfWriterUtil;
import by.cniitu.virtualexhibition.util.UserActionUtil;
import by.cniitu.virtualexhibition.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserActionService {

    @Autowired
    private JpaUserActionRepository userActionRepository;

    @Autowired
    private JpaFileRepository fileRepository;

    public void save(int userId, int fileId, int actionTypeId){
        userActionRepository.saveUserActionByIds(userId, fileId, actionTypeId);
    }

    public List<UserActionTo> getActionsByUserId(int exhibitionId, int userId){
        List<UserAction> actions = userActionRepository.getUserActionByExhibAndUserId(exhibitionId, userId);
        return actions.stream()
                .map(UserUtil::toUserActionTo)
                .collect(Collectors.toList());
    }

    public List<UserActionTo> getActionsByStandId(int standId){
        List<UserAction> actions = userActionRepository.getUserActionByStandId(standId);
        return actions.stream()
                .map(UserUtil::toUserActionTo)
                .collect(Collectors.toList());
    }

    public File getStatistics(User user, String type){
        List<UserActionTo> allUserActions = new ArrayList<>();

        for(Integer i : userActionRepository.getStandIdsByUser(user.getId())){
            allUserActions.addAll(getActionsByStandId(i));
        }
        if(type.equalsIgnoreCase("excel")){
            return UserActionUtil.saveActionsToFile(allUserActions);
        }
        if (type.equalsIgnoreCase("pdf")){
            return PdfWriterUtil.writeActionToPdf(allUserActions);
        }
        return null;
    }

    public File getFileAction(int userId){
        List<FileActionTo> fileActions = new ArrayList<>();
        List<by.cniitu.virtualexhibition.entity.file.File> files = fileRepository.getFilesByUserId(userId);

        for (by.cniitu.virtualexhibition.entity.file.File file : files){
            Integer actions = userActionRepository.getActionsByFileId(file.getId());
            String fileName = file.getPath();
            int idx = fileName.lastIndexOf("/");
            int idx2 = fileName.lastIndexOf(".");
            if(fileName.contains("~~~")){
                fileName = fileName.substring(idx + 1, idx2 - 13).concat(fileName.substring(idx2));
            }
            fileActions.add(new FileActionTo(fileName, actions));
        }
        return FileActionUtil.saveFileActionToFile(fileActions);
    }

}
