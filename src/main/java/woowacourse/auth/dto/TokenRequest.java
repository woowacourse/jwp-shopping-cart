package woowacourse.auth.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class TokenRequest {

    private String email;
    private String password;

    public static TokenRequest from(String email, String password) {
        return new TokenRequest(email, password);
    }
}
