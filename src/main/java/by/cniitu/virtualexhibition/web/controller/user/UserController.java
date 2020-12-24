package by.cniitu.virtualexhibition.web.controller.user;

import by.cniitu.virtualexhibition.config.jwt.JwtProvider;
import by.cniitu.virtualexhibition.entity.file.FileType;
import by.cniitu.virtualexhibition.entity.user.User;
import by.cniitu.virtualexhibition.repository.file.JpaFileTypeRepository;
import by.cniitu.virtualexhibition.service.FileService;
import by.cniitu.virtualexhibition.service.UserService;
import by.cniitu.virtualexhibition.to.UserTo;
import by.cniitu.virtualexhibition.util.JwtsUtil;
import by.cniitu.virtualexhibition.util.MailThreadExecutorUtil;
import by.cniitu.virtualexhibition.util.UserUtil;
import by.cniitu.virtualexhibition.util.ValidationUtil;
import by.cniitu.virtualexhibition.web.controller.file.FileNameAndType;
import by.cniitu.virtualexhibition.web.controller.file.FileUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Autowired
    private JpaFileTypeRepository fileTypeRepository;
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody RegisterRequest request) {
        User noConfirmUser = getUserByClaims(request.getToken(), "register");
        if (Objects.isNull(noConfirmUser)) {
            System.out.println("{\"message\": \"Error. User = null\"}");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (!ValidationUtil.isUserValid(noConfirmUser)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"User is not valid\"}");
        }
        if (userService.isEmailExist(noConfirmUser.getEmail())) {
            System.out.println("BAD_REQUEST User with such email exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"User with such email exists\"}");
        }
        MailThreadExecutorUtil.execute(() -> userService.confirmUserEmail(noConfirmUser));
        return ResponseEntity.ok("{\"message\": \"Ok. Check your email please\"}");
    }

    @GetMapping("/activate/{token}")
    public ResponseEntity<String> activate(@PathVariable String token) {
        // TODO cancel it
        if (token.equals("1111"))
            return ResponseEntity.ok("{\"message\": \"Your email is confirmed\"}");
        User newUser = UserUtil.getUserByParseCode(token);
        if (newUser == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"Your link is invalid\"}");
        try {
            userService.create(newUser);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"" + ex.getMessage() + "\"}");
        }
        return ResponseEntity.ok("{\"message\": \"Your email is confirmed\"}");
    }

    //TODO DELETE
    @PostMapping("/auth_old")
    public AuthResponse auth(@RequestBody User userRequest) {
        User user = userService.findByLoginAndPassword(userRequest.getEmail(), userRequest.getPassword()); //check user for null
        String token = jwtProvider.generateToken(user.getEmail(), user.getPassword());
        return new AuthResponse(token);
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@RequestBody AuthRequest request) {
        System.out.println("[UMKA] request.getToken() = " + request.getToken());
        User user = getUserByClaims(request.getToken(), "auth");
        System.out.println("[UMKA] user = " + user);
        if (Objects.isNull(user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"error\"}");
        }
        Integer id = user.getId();
        if (UserUtil.userIdWithWebsocket.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"User with such email already logged in\"}");
        }
        System.out.println("[UMKA] start response token generation " + user);
        String token = jwtProvider.generateToken(user.getEmail(), user.getPassword());
        System.out.println("[UMKA] response token generated " + token);
        String nickName = user.getNickName();
        String role = user.getRole().getName();
        System.out.println("[UMKA] login successful " + user);
        return ResponseEntity.ok(new AuthResponse(id, token, nickName, role));
    }

    @PostMapping("/changepassword")
    public ResponseEntity<Object> changePassword(@RequestBody AuthRequest request) {
        User user = getUserByClaims(request.getToken(), "changepassword");
        if (Objects.isNull(user)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"error\"}");
        }
        String newPassword = RandomString.make() + new Random().nextInt(9);
        user.setPassword(newPassword);
        MailThreadExecutorUtil.execute(() -> userService.changeUserPassword(user, newPassword));
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"OK\"}");
    }

    /**
     * @param type  - can be only "name", "surname", "email", "nick"
     * @param query - search query
     */
    @CrossOrigin("*")
    @GetMapping("/users")
    public ResponseEntity<Object> getUserByType(@RequestParam String type, @RequestParam String query) {
        List<User> users = userService.getUserByType(type, query);
        if (users == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"Wrong type\"}");
        }
        return ResponseEntity.ok(users);
    }

    @PostMapping("/changeavatar")
    public ResponseEntity<Object> changeAvatar(@RequestParam Integer userId, @RequestParam Integer avatarId) {
        User user = userService.get(userId);
        if (Objects.isNull(user)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"No such user\"}");
        }
        user.setAvatarId(avatarId);
        userService.update(user);
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"OK\"}");
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getUser(@PathVariable int id){
        UserTo userTo = userService.getUserTo(id);
        if (Objects.isNull(userTo)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"No such user\"}");
        }
        return ResponseEntity.ok(userTo);
    }

    private User getUserByClaims(String token, String method) {
        Claims claims = JwtsUtil.getClaims(token);
        if (Objects.isNull(claims)) {
            return null;
        }
        User user = null;
        if (method.equals("auth")) {
            String email = claims.get("email").toString();
            String password = claims.get("password").toString();
            user = userService.findByLoginAndPassword(email, password);
        }
        if (method.equals("changepassword")) {
            String email = claims.get("email").toString();
            user = (User) userService.loadUserByUsername(email);
        }
        if (method.equals("register")) {
            String firstName = claims.get("name").toString();
            String lastName = claims.get("surname").toString();
            String nickName = claims.get("nickname").toString();
            String email = claims.get("email").toString();
            String password = claims.get("password").toString();

            user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setNickName(nickName);
            user.setEmail(email);
            user.setPassword(password);
        }
        return user;
    }


    //TODO DELETE
    @GetMapping("/authtoken")
    public String auth(@RequestParam("l") String login,
                       @RequestParam("p") String password,
                       @RequestParam("fn") String firstName,
                       @RequestParam("ln") String lastName,
                       @RequestParam("nn") String nickName
    ) {
        Map<String, Object> claims = new HashMap<String, Object>() {{
            put("email", login);
            put("password", password);
            put("name", firstName);
            put("surname", lastName);
            put("nickname", nickName);
        }};
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "secret".getBytes())
                .compact();
    }

}
