package finances_app.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsersController {
    @Autowired
    UsersRepository userRepo;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";
    private String username_exists = "{\"message\":\"username already in use\"}";


    @GetMapping(path = "/Users")
    List<Users> getAllUsers() {
        return userRepo.findAll();
    }

    @GetMapping(path = "/Users/{username}")
    Users getUserByUsername(@PathVariable String username) {
        Users user = userRepo.findByUsername(username);
        return user;
    }


    @PostMapping(path = "/Users")
    String postUser(@RequestBody Users users){
        if (userRepo.findByUsername(users.getUsername()) == null) {
            userRepo.save(users);
            return success;
        } else {
            return username_exists;
        }
    }

    @DeleteMapping(path = "/Users/{username")
    String deleteUserByUsername(@PathVariable String username) {
        Users user = userRepo.findByUsername(username);
        if (user == null) {
            return failure;
        } else {
            userRepo.delete(user);
            return success;
        }
    }
}
