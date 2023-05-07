package cart.auth;

import cart.auth.dto.AuthInfo;
import cart.presentation.dto.MemberResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

public class AuthController {
    private BasicAuthorizationExtractor basicAuthorizationExtractor = new BasicAuthorizationExtractor();
    private AuthService authService;

    @GetMapping("/members/my")
    public ResponseEntity findMyInfo(HttpServletRequest request) {
        AuthInfo authInfo = basicAuthorizationExtractor.extract(request);
        String email = authInfo.getEmail();
        String password = authInfo.getPassword();

        if (authService.checkInvalidLogin(email, password)) {
            throw new AuthorizationException();
        }

        MemberResponse member = authService.findMember(email);
        return ResponseEntity.ok().body(member);
    }
}
