package cart.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UnAuthenticationException extends RuntimeException {

    private final String message = "로그인이 필요합니다";
}
