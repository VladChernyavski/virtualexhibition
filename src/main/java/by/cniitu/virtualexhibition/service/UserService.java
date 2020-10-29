package by.cniitu.virtualexhibition.service;

import by.cniitu.virtualexhibition.entity.user.User;
import by.cniitu.virtualexhibition.entity.user.UserRole;
import by.cniitu.virtualexhibition.repository.user.RoleRepository;
import by.cniitu.virtualexhibition.repository.user.UserRepository;
import by.cniitu.virtualexhibition.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Base64;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Environment environment;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.getByEmail(email.toLowerCase());
    }

    public User findByLoginAndPassword(String email, String password) {
        User user = (User) loadUserByUsername(email);

        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public void delete(int id) {
        userRepository.delete(id);
    }

    public User get(int id) {
        return userRepository.get(id);
    }

    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    public User create(User user) {
        return prepareAndSave(user);
    }

    public User update(User user){
        return userRepository.save(UserUtil.prepareToSave(user, passwordEncoder));
    }

    public void confirmUserEmail(User user) {
        //check data on empty values
        String email = user.getEmail();
        String data = user.getFirstName() + "~"
                + user.getLastName() + "~"
                + user.getNickName() + "~"
                + email + "~"
                + user.getPassword();
        String encode = Base64.getEncoder().encodeToString(data.getBytes());


        String message = String.format("Hello, %s! \n" +
                        "Welcome to Virtual Exhibition. Please, visit next link: http://" +
                        environment.getProperty("server.host") +
                        ":" + environment.getProperty("local.server.port") + "/activate/%s",
                email, encode);

        try {
            mailService.send(email, "Activation code", message);
        } catch (MailException e){
            System.out.println(e.getMessage());
        }

    }

    public boolean isEmailExist(String email){
        return loadUserByUsername(email) != null;
    }

    public void changeUserPassword(User user, String newPassword){
        String email = user.getEmail();
        String message = String.format("Hello, %s! \n" +
                "Your password was changed. Your new password is %s", email, newPassword);
        try {
            mailService.send(email, "Your password was changed", message);
        } catch (MailException e){
            System.out.println(e.getMessage());
        }
        update(user);
    }

    private User prepareAndSave(User user) {
        UserRole role = roleRepository.findByName("ROLE_USER");
        user.setRole(role);
        return userRepository.save(UserUtil.prepareToSave(user, passwordEncoder));
    }

}
