package cart.controller;

import cart.controller.dto.UserSaveRequest;
import cart.service.UserService;
import cart.service.dto.UserSaveDto;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> saveUser(@Valid @RequestBody final UserSaveRequest userSaveRequest) {
        this.userService.save(UserSaveDto.from(userSaveRequest));
        return ResponseEntity.ok().build();
    }
}
