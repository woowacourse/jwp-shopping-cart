package cart.controller;

import cart.dto.request.LoginRequestDto;
import cart.dto.request.SignUpRequestDto;
import cart.dto.response.UserResponseDto;
import cart.exception.DuplicateEmailException;
import cart.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private final static String REDIRECT_URI = "/settings";

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<Void> signUp(@RequestBody @Valid final SignUpRequestDto signUpRequestDto) {
        userService.signUp(signUpRequestDto);
        return ResponseEntity.created(URI.create(REDIRECT_URI)).build();
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody @Valid final LoginRequestDto loginRequestDto) {
        final UserResponseDto response = userService.login(loginRequestDto);
        return ResponseEntity.created(URI.create(REDIRECT_URI)).body(response);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<String> handle(DuplicateEmailException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
