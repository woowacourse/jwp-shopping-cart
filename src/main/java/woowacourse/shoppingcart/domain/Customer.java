package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.domain.customer.EncoderPassword;
import woowacourse.shoppingcart.domain.customer.Nickname;

public class Customer {

    private Long id;
    private final Email email;
    private final EncoderPassword password;
    private final Nickname nickname;

    public Customer(String email, String encoderPassword, String nickname) {
        this(null, email, encoderPassword, nickname);
    }

    public Customer(Long id, String email, String encoderPassword, String nickname) {
        this.id = id;
        this.email = new Email(email);
        this.password = new EncoderPassword(encoderPassword);
        this.nickname = new Nickname(nickname);
    }

    public void equalPrevPassword(String prePassword) {
        if (!password.isSamePassword(prePassword)) {
            throw new IllegalArgumentException("[ERROR] 이전 비밀번호와 일치하지 않습니다.");
        }
    }

    public void nonEqualNewPassword(String newPassword) {
        if (password.isSamePassword(newPassword)) {
            throw new IllegalArgumentException("[ERROR] 새로운 비밀번호가 이전 비밀번호와 동일합니다.");
        }
    }

    public Customer updateCustomer(String nickname) {
        return new Customer(id, email.getValue(), password.getValue(), nickname);
    }

    public Customer updatePassword(String newPassword) {
        return new Customer(id, email.getValue(), newPassword, nickname.getValue());
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }

    public String getNickname() {
        return nickname.getValue();
    }


    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
