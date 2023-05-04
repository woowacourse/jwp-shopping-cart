package cart.controller;

import cart.controller.dto.UserRequest;
import cart.controller.dto.UserResponse;
import cart.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> addUser(@RequestBody @Valid UserRequest userRequest) {
        userService.saveUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create("/settings"))
                             .build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> loadAllUser() {
        List<UserResponse> users = userService.loadAllUser();
        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(users);
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserResponse> loadUser(@PathVariable final Long userId) {
        UserResponse user = userService.loadUser(userId);
        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(user);
    }
}
