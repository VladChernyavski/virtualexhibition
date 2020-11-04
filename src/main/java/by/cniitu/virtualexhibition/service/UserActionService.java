package by.cniitu.virtualexhibition.service;

import by.cniitu.virtualexhibition.entity.user.UserAction;
import by.cniitu.virtualexhibition.repository.action.JpaUserActionRepository;
import by.cniitu.virtualexhibition.to.UserActionTo;
import by.cniitu.virtualexhibition.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserActionService {

    @Autowired
    private JpaUserActionRepository userActionRepository;

    public void save(int userId, int fileId, int actionTypeId){
        userActionRepository.saveUserActionByIds(userId, fileId, actionTypeId);
    }

    public List<UserActionTo> getActionsByUserId(int id){
        return userActionRepository.getAllByUserId(id).stream()
                .map(UserUtil::toUserActionTo)
                .collect(Collectors.toList());
    }



}
