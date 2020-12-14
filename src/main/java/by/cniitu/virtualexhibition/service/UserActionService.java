package by.cniitu.virtualexhibition.service;

import by.cniitu.virtualexhibition.entity.user.User;
import by.cniitu.virtualexhibition.entity.user.UserAction;
import by.cniitu.virtualexhibition.repository.action.JpaUserActionRepository;
import by.cniitu.virtualexhibition.to.UserActionTo;
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

    public File getStatistics(User user){
        List<UserActionTo> allUserActions = new ArrayList<>();

        for(Integer i : userActionRepository.getStandIdsByUser(user.getId())){
            allUserActions.addAll(getActionsByStandId(i));
        }
        return UserActionUtil.saveActionsToFile(allUserActions);
    }



}
