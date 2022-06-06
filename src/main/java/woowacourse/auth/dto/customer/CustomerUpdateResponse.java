package woowacourse.auth.dto.customer;

import lombok.Getter;

@Getter
public class CustomerUpdateResponse {
    private String nickname;

    public CustomerUpdateResponse() {
    }

    public CustomerUpdateResponse(String nickname) {
        this.nickname = nickname;
    }
}

