package by.cniitu.virtualexhibition.web.controller.user;

import by.cniitu.virtualexhibition.config.jwt.JwtProvider;
import by.cniitu.virtualexhibition.entity.user.User;
import by.cniitu.virtualexhibition.service.UserService;
import by.cniitu.virtualexhibition.util.JwtsUtil;
import by.cniitu.virtualexhibition.util.MailThreadExecutorUtil;
import by.cniitu.virtualexhibition.util.UserUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody RegisterRequest request) {
        User noConfirmUser = getUserByClaims(request.getToken(), "register");
        if(Objects.isNull(noConfirmUser)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if(userService.isEmailExist(noConfirmUser.getEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with such email exists");
        }
        MailThreadExecutorUtil.execute(() -> userService.confirmUserEmail(noConfirmUser));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code) {
        User newUser = UserUtil.getUserByParseCode(code);
        userService.create(newUser);
        return "Your email is confirmed"; // TODO redirect to main page
    }

    @PostMapping("/auth_old")
    public AuthResponse auth(@RequestBody User userRequest) {
        User user = userService.findByLoginAndPassword(userRequest.getEmail(), userRequest.getPassword()); //check user for null
        String token = jwtProvider.generateToken(user.getEmail(), user.getPassword());
        return new AuthResponse(token);
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@RequestBody AuthRequest request) {
        User user = getUserByClaims(request.getToken(), "auth");
        if (Objects.isNull(user)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        String token = jwtProvider.generateToken(user.getEmail(), user.getPassword());
        String nickName = user.getNickName();
        String role = user.getRole().getName();
        return ResponseEntity.ok(new AuthResponse(token, nickName, role)); //добавить в ответ роль и никнейм
    }

    @PostMapping("/changepassword")
    public ResponseEntity<Object> changePassword(@RequestBody AuthRequest request) {
        User user = getUserByClaims(request.getToken(), "changepassword");
        if (Objects.isNull(user)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        String newPassword = RandomString.make();
        user.setPassword(newPassword);
        MailThreadExecutorUtil.execute(() -> userService.changeUserPassword(user, newPassword));
        return ResponseEntity.status(HttpStatus.OK).build();
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
        if (method.equals("register")){
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









    //DELETE
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
