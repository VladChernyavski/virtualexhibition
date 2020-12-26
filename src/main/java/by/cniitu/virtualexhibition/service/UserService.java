package by.cniitu.virtualexhibition.service;

import by.cniitu.virtualexhibition.entity.user.User;
import by.cniitu.virtualexhibition.entity.user.UserRole;
import by.cniitu.virtualexhibition.repository.user.JpaRoleRepository;
import by.cniitu.virtualexhibition.repository.user.JpaUserRepository;
import by.cniitu.virtualexhibition.to.UserTo;
import by.cniitu.virtualexhibition.util.JwtsUtil;
import by.cniitu.virtualexhibition.util.UserUtil;
import by.cniitu.virtualexhibition.web.controller.file.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private JpaUserRepository userRepository;
    @Autowired
    private JpaRoleRepository roleRepository;
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
        userRepository.deleteById(id);
    }

    public User get(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserTo getUserTo(int id){
        User user = get(id);
        if(user == null){
            return null;
        }
        UserTo userTo = new UserTo(user.getId(), user.getFirstName(), user.getLastName(),
                user.getNickName(), user.getEmail(), user.getRole().getName(), user.getAvatarId());
        return userTo;
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
        String dataToken = JwtsUtil.generateTokenForUser(data);

        // TODO use port number 80 at the end
        // here we use the port of site user interface
        String message = String.format("Hello, %s! \n" +
                        "Welcome to Virtual Exhibition. Please, visit next link: http://" +
                        FileUtil.getExternalIp() + ":3001/activate/%s", email, dataToken);

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

    public List<User> getUserByType(String type, String query){
        if(type.equalsIgnoreCase("name")){
            return userRepository.getUserByName(query);
        }
        if(type.equalsIgnoreCase("surname")){
            return userRepository.getUserBySurname(query);
        }
        if(type.equalsIgnoreCase("email")){
            return userRepository.getUserByEmail(query);
        }
        if(type.equalsIgnoreCase("nick")){
            return userRepository.getUserByNickName(query);
        }
        return null;
    }

    private User prepareAndSave(User user) {
        UserRole role = roleRepository.findByName("ROLE_USER");
        user.setRole(role);
        return userRepository.save(UserUtil.prepareToSave(user, passwordEncoder));
    }

}
