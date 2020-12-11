package by.cniitu.virtualexhibition.web.controller.subscription;

import by.cniitu.virtualexhibition.entity.user.User;
import by.cniitu.virtualexhibition.service.SubscriptionService;
import by.cniitu.virtualexhibition.service.UserService;
import by.cniitu.virtualexhibition.util.MailThreadExecutorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SubscriptionController {

    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    public ResponseEntity<Object> subscribe(@RequestBody SubscribeAndUnsubscribeRequest request) {
        //пользователь на которого подписываються
        Integer userId = request.getUserId();
        //пользователь который подписываеться
        Integer subscriberId = request.getSubscriberId();

        User user = userService.get(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"No user with id" + userId + "\"}");
        }
        String role = user.getRole().getName();
        if (!role.equalsIgnoreCase("role_advertiser") && !role.equalsIgnoreCase("role_vendor")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"User must have to role advertiser or vendor\"}");
        }

        if (subscriptionService.isUserSubscribed(userId, subscriberId)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"You already subscribed to this user\"}");
        }

        subscriptionService.subscribe(userId, subscriberId);
        return ResponseEntity.ok("{\"message\": \"You successfully subscribed\"}");
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<Object> unsubscribe(@RequestBody SubscribeAndUnsubscribeRequest request) {
        //пользователь от которого отписываються
        Integer userId = request.getUserId();
        //пользователь который отписываеться
        Integer subscriberId = request.getSubscriberId();
        if (!subscriptionService.isUserSubscribed(userId, subscriberId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"You don't subscribe to this user\"}");
        }
        subscriptionService.unsubscribe(userId, subscriberId);
        return ResponseEntity.ok("{\"message\": \"You successfully unsubscribed\"}");
    }

    @PostMapping("/sendemail")
    public ResponseEntity<Object> sendEmailToSubscribers(@RequestBody SendEmailSubscribersRequest request) {
        User user = userService.get(request.getUserId());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"No user with id" + request.getUserId() + "\"}");
        }
        String role = user.getRole().getName();
        if (!role.equalsIgnoreCase("role_advertiser") && !role.equalsIgnoreCase("role_vendor")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"User must have to role advertiser or vendor\"}");
        }
        MailThreadExecutorUtil.execute(()
                -> subscriptionService.sendEmailToSubscribers(user.getEmail(), request.getSubject(),
                request.getMessage(), user.getSubscribers())
        );

        return ResponseEntity.ok("{\"message\": \"Email was send to all subscribers\"}");
    }

    //возвращает список всех подписчиков по id пользователя
    @GetMapping("user/{id}/subscribers")
    public ResponseEntity<Object> getUserSubscribers(@PathVariable int id) {
        User user = userService.get(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"No such user in db\"}");
        }
        String role = user.getRole().getName();
        if (!role.equalsIgnoreCase("role_advertiser") && !role.equalsIgnoreCase("role_vendor")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"Only advertiser and vendor can have subscribers\"}");
        }

        return ResponseEntity.ok(user.getSubscribers());
    }

    //возвращает список всех подписок по id пользователя
    @GetMapping("user/{id}/subscriptions")
    public ResponseEntity<Object> getUserSubscriptions(@PathVariable int id) {
        User user = userService.get(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"No such user in db\"}");
        }
        return ResponseEntity.ok(user.getSubscriptions());
    }

}
