package by.cniitu.virtualexhibition.service;

import by.cniitu.virtualexhibition.entity.user.User;
import by.cniitu.virtualexhibition.repository.user.JpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SubscriptionService {

    @Autowired
    private JpaUserRepository userRepository;

    @Autowired
    private MailService mailService;

    public void subscribe(int userId, int subscriberId){
        userRepository.subscribe(userId, subscriberId);
    }

    public void unsubscribe(int userId, int subscriberId){
        userRepository.unsubscribe(subscriberId, userId);
    }

    public void sendEmailToSubscribers(String emailFrom, String subject, String message, Set<User> emailTo){
        try {
            for(User u : emailTo){
                mailService.sendEmailToSubscriber(emailFrom, u.getEmail(), subject, message);
            }
        } catch (MailException e){
            System.out.println(e.getMessage());
        }
    }

    public boolean isUserSubscribed(int userId, int subscriberId){
        return userRepository.isUserSubscribed(userId, subscriberId);
    }

}
