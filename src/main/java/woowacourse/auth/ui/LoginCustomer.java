package woowacourse.auth.ui;

import javax.validation.constraints.Positive;

public class LoginCustomer {

    @Positive(message = "[ERROR] 양수 값이 아닙니다.")
    private Long id;

    public LoginCustomer() {
    }

    public LoginCustomer(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
