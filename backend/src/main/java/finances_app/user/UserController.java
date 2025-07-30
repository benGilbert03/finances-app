package finances_app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepo;

    private String success = "{\"message\":\"success\"}";
    private String username_exists = "{\"message\":\"username already in use\"}";


    @GetMapping(path = "/User")
    List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @PostMapping(path = "/User")
    String postUser(@RequestBody User user){
        if (userRepo.findByUsername(user.getUsername()) != null) {
            userRepo.save(user);
            return success;
        } else {
            return username_exists;
        }
    }
}
