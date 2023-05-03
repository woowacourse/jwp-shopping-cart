package cart.entity;

import cart.vo.Email;
import cart.vo.Password;

public class Member {

    private final Email email;
    private final Password password;

    public Member(Email email, Password password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }

    public static class Builder {

        private Email email;
        private Password password;

        public Builder email(Email email) {
            this.email = email;
            return this;
        }

        public Builder password(Password password) {
            this.password = password;
            return this;
        }

        public Member build() {
            return new Member(email, password);
        }

    }

}
