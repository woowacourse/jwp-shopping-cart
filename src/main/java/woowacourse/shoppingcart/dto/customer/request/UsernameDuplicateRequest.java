package woowacourse.shoppingcart.dto.customer.request;

import javax.validation.constraints.Pattern;

public class UsernameDuplicateRequest {

    @Pattern(regexp = "^[a-z0-9_-]{5,20}$",
            message = "유저 네임 형식이 올바르지 않습니다. (영문 소문자, 숫자와 특수기호(_), (-)만 사용 가능, 5자 이상 20자 이내)")
    private String username;

    public UsernameDuplicateRequest() {
    }

    public UsernameDuplicateRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
