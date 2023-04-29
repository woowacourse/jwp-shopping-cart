package cart.controller;

import cart.controller.dto.SignInRequest;
import cart.controller.dto.SignInResponse;
import cart.service.UserService;
import cart.service.dto.UserDto;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final String BASIC_DELIMITER = ":";

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponse> signIn(@RequestBody @Valid SignInRequest signInRequest) {
        UserDto userDto = userService.signIn(signInRequest.getEmail(), signInRequest.getPassword());

        return ResponseEntity.ok(new SignInResponse(encode(userDto)));
    }

    private String encode(final UserDto userDto) {
        String target = userDto.getEmail()
                .concat(BASIC_DELIMITER)
                .concat(userDto.getPassword());
        byte[] basic = Base64.getEncoder()
                .encode(target.getBytes(StandardCharsets.UTF_8));

        return new String(basic);
    }
}
