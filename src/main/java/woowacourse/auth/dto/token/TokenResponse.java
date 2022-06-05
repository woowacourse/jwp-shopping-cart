package woowacourse.auth.dto.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class TokenResponse {
    private String nickname;
    private String accessToken;

    public TokenResponse() {
    }

    public TokenResponse(String nickname, String accessToken) {
        this.nickname = nickname;
        this.accessToken = accessToken;
    }
}
