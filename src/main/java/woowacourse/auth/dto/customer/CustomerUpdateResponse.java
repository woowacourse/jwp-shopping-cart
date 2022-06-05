package woowacourse.auth.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class CustomerUpdateResponse {
    private String nickname;

    public CustomerUpdateResponse() {
    }

    public CustomerUpdateResponse(String nickname) {
        this.nickname = nickname;
    }
}

