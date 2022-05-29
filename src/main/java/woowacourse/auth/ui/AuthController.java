package woowacourse.auth.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.SignUpRequest;
import woowacourse.auth.dto.SignUpResponse;
import woowacourse.auth.utils.EmailUtil;

@RestController
@RequestMapping("/users")
public class AuthController {
}
